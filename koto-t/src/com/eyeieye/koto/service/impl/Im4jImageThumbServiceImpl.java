package com.eyeieye.koto.service.impl;

import com.eyeieye.koto.common.ImgType;
import com.eyeieye.koto.domain.BytesStoredImage;
import com.eyeieye.koto.domain.StoredImage;
import com.eyeieye.koto.remote.UploadSource;
import com.eyeieye.koto.service.ImageThumbService;
import com.eyeieye.melody.util.ShortUUIDGenerator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.MogrifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

public class Im4jImageThumbServiceImpl implements ImageThumbService {
	private static final Log logger = LogFactory.getLog(Im4jImageThumbServiceImpl.class);
	private boolean useGM;
	private File tmpDir;
	private double quality;
	private boolean extent;
	private boolean enlarge;
	private static final char shrinkLargerImagesFlag = '>';
	private static Pattern OutPutCell = Pattern.compile("\\[\\[(.+?)\\]\\]");

	public Im4jImageThumbServiceImpl() {
		this.useGM = false;

		this.quality = 90.0D;

		this.extent = true;

		this.enlarge = false;
	}

	public void setUseGM(boolean useGM) {
		this.useGM = useGM;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public void setExtent(boolean extent) {
		this.extent = extent;
	}

	public void setEnlarge(boolean enlarge) {
		this.enlarge = enlarge;
	}

	public void setTmpDir(String tmpDir) {
		this.tmpDir = new File(tmpDir);
		if (!this.tmpDir.exists()) {
			throw new IllegalArgumentException("directory:" + tmpDir + " not exists.");
		}

		if (!this.tmpDir.canWrite())
			throw new IllegalArgumentException("directory:" + tmpDir + " can't write.");
	}

	public StoredImage getImageThumb(StoredImage source, Integer newWidth, Integer newHeight) throws IOException {
		File sourceFile = writeToTmp(source);

		Double thumbQuality = null;
		ImgType type = ImgType.valueOf(source.getFormat());
		if ((type != null) && ((type == ImgType.jpeg) || (type == ImgType.png))) {
			thumbQuality = Double.valueOf(this.quality);
		}

		File thumb = convert(sourceFile, newWidth, newHeight, thumbQuality);
		try {
			return readFromFile(thumb, source);
		} finally {
			tryRemoveTmpFile(new File[] { sourceFile, thumb });
		}
	}

	private File convert(File sourceFile, Integer newWidth, Integer newHeight, Double quality) throws IOException {
		if (newHeight == null) {
			newHeight = newWidth;
		}
		String thubmFileName = ShortUUIDGenerator.generate();
		File tmpFile = new File(this.tmpDir, thubmFileName);

		IMOperation op = new IMOperation();
		op.addImage(new String[] { sourceFile.toString() });
		if (newHeight.intValue() == 0) {
			if (this.enlarge)
				op.resize(newWidth);
			else
				op.resize(newWidth, null, Character.valueOf('>'));
		} else {
			if (this.enlarge)
				op.resize(newWidth, newHeight);
			else {
				op.resize(newWidth, newHeight, Character.valueOf('>'));
			}
			if (this.extent) {
				op.gravity("center");
				op.extent(newWidth, newHeight);
			}
		}
		if (quality != null) {
			op.quality(quality);
		}

		op.addImage(new String[] { tmpFile.toString() });
		ConvertCmd convert = new ConvertCmd(this.useGM);
		try {
			convert.run(op, new Object[0]);
		} catch (InterruptedException e) {
			logger.error("Interrupted then convert", e);
		} catch (IM4JavaException e) {
			throw new IOException(e);
		}
		return tmpFile;
	}

	private File writeToTmp(StoredImage source) throws IOException {
		String tmpFileName = ShortUUIDGenerator.generate();
		File tmpFile = new File(this.tmpDir, tmpFileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tmpFile);
			source.write(fos);
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (Exception ignore) {
				}
		}
		return tmpFile;
	}

	private StoredImage readFromFile(File thumb, StoredImage source) throws IOException {
		BytesStoredImage back = new BytesStoredImage();
		byte[] body = FileUtils.readFileToByteArray(thumb);
		back.setBody(body);
		back.setFormat(source.getFormat());
		return back;
	}

	private void tryRemoveTmpFile(File[] files) {
		for (File f : files)
			try {
				f.delete();
			} catch (Exception ignore) {
			}
	}

	public String preHanleAndGetFormat(UploadSource imgFile) throws IOException {
		if (imgFile == null) {
			throw new NullPointerException("imgFile can't be null.");
		}
		byte[] body = imgFile.getBody();
		if ((body == null) || (body.length == 0)) {
			throw new IllegalArgumentException("image body can't be null or empty.");
		}

		File image = writeToTmp(body);
		try {
			ImageInfo info = preHanle(image);
			body = FileUtils.readFileToByteArray(image);
			imgFile.setBody(body);
			return info.format.toLowerCase();
		} finally {
			tryRemoveTmpFile(new File[] { image });
		}
	}

	private ImageInfo preHanle(File image) throws IOException {
		ImageInfo info = getInfo(image);
		IMOperation op = new IMOperation();

		op.strip();

		if (info.isCMYK()) {
			op.colorspace("sRGB");
		}
		op.addImage();
		MogrifyCmd mogrifyCmd = new MogrifyCmd(this.useGM);
		try {
			mogrifyCmd.run(op, new Object[] { image.toString() });
		} catch (InterruptedException e) {
			logger.error("Interrupted then preHanle", e);
		} catch (IM4JavaException e) {
			throw new IOException(e);
		}
		return info;
	}

	private File writeToTmp(byte[] source) throws IOException {
		String tmpFileName = ShortUUIDGenerator.generate();
		File tmpFile = new File(this.tmpDir, tmpFileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tmpFile);
			fos.write(source);
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (Exception ignore) {
				}
		}
		return tmpFile;
	}

	private ImageInfo getInfo(File image) throws IOException {
		IMOperation op = new IMOperation();

		op.format("[[%m]][[%r]]");
		op.addImage();
		IdentifyCmd identifyCmd = new IdentifyCmd(this.useGM);
		ArrayListOutputConsumer output = new ArrayListOutputConsumer();
		identifyCmd.setOutputConsumer(output);
		try {
			identifyCmd.run(op, new Object[] { image.toString() });
		} catch (InterruptedException e) {
			logger.error("Interrupted then get format", e);
		} catch (IM4JavaException e) {
			throw new IOException(e);
		}
		ArrayList cmdOutput = output.getOutput();
		if ((cmdOutput == null) || (cmdOutput.isEmpty())) {
			return null;
		}
		String outputString = (String) cmdOutput.get(0);

		Matcher m = OutPutCell.matcher(outputString);
		ImageInfo info = new ImageInfo();
		if (m.find()) {
			info.format = m.group(1).toLowerCase();
		}
		if (m.find()) {
			info.classAndColorspace = m.group(1).toLowerCase();
		}
		return info;
	}

	public StoredImage cutImage(StoredImage source, Integer newWidth, Integer newHeight, String isGray)
			throws InterruptedException, IM4JavaException {
		File sourceFile = null;
		try {
			sourceFile = writeToTmp(source);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Double thumbQuality = null;
		ImgType type = ImgType.valueOf(source.getFormat());
		if ((type != null) && ((type == ImgType.jpeg) || (type == ImgType.png))) {
			thumbQuality = Double.valueOf(this.quality);
		}

		File thumb = null;
		try {
			thumb = convertCut(sourceFile, newWidth, newHeight, thumbQuality, isGray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return readFromFile(thumb, source);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			tryRemoveTmpFile(new File[] { sourceFile, thumb });
		}
		return source;
	}

	private File convertCut(File sourceFile, Integer newWidth, Integer newHeight, Double quality, String isGray)
			throws IOException, InterruptedException, IM4JavaException {
		String thubmFileName = ShortUUIDGenerator.generate();
		File tmpFile = new File(this.tmpDir, thubmFileName);

		BufferedImage buffImg = null;
		buffImg = ImageIO.read(sourceFile);
		int width = 0;
		int height = 0;
		width = buffImg.getWidth();
		height = buffImg.getHeight();
		if ((newWidth.intValue() == 0) || (newHeight.intValue() == 0)) {
			newWidth = Integer.valueOf(width);
			newHeight = Integer.valueOf(height);
		}
		int tempHeight = newHeight.intValue();
		int tempWidth = newWidth.intValue();
		height = (int) Math.round(height * tempWidth * 1.0D / width);
		width = tempWidth;
		if (height < tempHeight) {
			width = (int) Math.round(width * tempHeight * 1.0D / height);
			height = tempHeight;
		}

		IMOperation op = new IMOperation();
		op.addImage(new String[] { sourceFile.toString() });

		op.resize(Integer.valueOf(width), Integer.valueOf(height));
		op.gravity("center");
		op.crop(newWidth, newHeight, Integer.valueOf(0), Integer.valueOf(0));
		if ("1".equalsIgnoreCase(isGray)) {
			op.colorspace("Gray");
		}
		if (quality != null) {
			op.quality(quality);
		}

		op.addImage(new String[] { tmpFile.toString() });
		ConvertCmd convert = new ConvertCmd(this.useGM);
		try {
			convert.run(op, new Object[0]);
		} catch (InterruptedException e) {
			logger.error("Interrupted then convert", e);
		} catch (IM4JavaException e) {
			throw new IOException(e);
		}
		return tmpFile;
	}

	private static final class ImageInfo {
		private String format;
		private String classAndColorspace;

		private boolean isCMYK() {
			return this.classAndColorspace.toLowerCase().contains("cmyk");
		}
	}
}

/*
 * Location: E:\codes\work\koto\WEB-INF\classes\ Qualified Name:
 * com.eyeieye.koto.service.impl.Im4jImageThumbServiceImpl JD-Core Version:
 * 0.6.2
 */