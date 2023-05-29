package com.daniel.poc.img;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class NKImgProcessorImpl implements NKImgProcessor{

    // Method to resize an image
    public File resizeImage(File inputFile, File outputFile, int newWidth, int newHeight) throws IOException {
        BufferedImage inputImage = ImageIO.read(inputFile);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());
        outputImage.getGraphics().drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        ImageIO.write(outputImage, "jpg", outputFile);
        return outputFile;
    }

    // Method to convert an image to grayscale
    public File convertToGrayscale(File inputFile, File outputFile) throws IOException {
        BufferedImage inputImage = ImageIO.read(inputFile);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        outputImage.getGraphics().drawImage(inputImage, 0, 0, null);
        ImageIO.write(outputImage, "jpg", outputFile);
        return outputFile;
    }

    // Method to flip an image horizontally
    public File flipHorizontally(File inputFile, File outputFile) throws IOException {
        BufferedImage inputImage = ImageIO.read(inputFile);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                outputImage.setRGB(x, y, inputImage.getRGB(inputImage.getWidth() - x - 1, y));
            }
        }
        ImageIO.write(outputImage, "jpg", outputFile);
        return outputFile;
    }

    // Method to apply a sepia filter to an image
    public File applySepiaFilter(File inputFile, File outputFile) throws IOException {
        BufferedImage inputImage = ImageIO.read(inputFile);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
        Graphics2D graphics = outputImage.createGraphics();
        graphics.drawImage(inputImage, 0, 0, null);
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                Color color = new Color(inputImage.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);
                if (tr > 255) {
                    tr = 255;
                }
                if (tg > 255) {
                    tg = 255;
                }
                if (tb > 255) {
                    tb = 255;
                }
                Color newColor = new Color(tr, tg, tb);
                outputImage.setRGB(x, y, newColor.getRGB());
            }
        }
        ImageIO.write(outputImage, "jpg", outputFile);
        return outputFile;
    }

    // Method to convert an image to a different format
    public File convertImageFormat(File inputFile, File outputFile, String outputFormat) throws IOException {
        BufferedImage inputImage = ImageIO.read(inputFile);
        ImageIO.write(inputImage, outputFormat, outputFile);
        return outputFile;
    }

    // Method to merge two images horizontally
    public File mergeImages(List<File> inputFiles, File outputFile) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        int maxHeight = 0;
        int totalWidth = 0;
        for (File file : inputFiles) {
            BufferedImage image = ImageIO.read(file);
            images.add(image);
            maxHeight = Math.max(maxHeight, image.getHeight());
            totalWidth += image.getWidth();
        }
        BufferedImage outputImage = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = outputImage.createGraphics();
        int x = 0;
        for (BufferedImage image : images) {
            graphics.drawImage(image, x, 0, null);
            x += image.getWidth();
        }
        graphics.dispose();
        ImageIO.write(outputImage, "jpg", outputFile);
        return outputFile;
    }

}