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

public interface NKImgProcessor  {

    // Method to resize an image
    public File resizeImage(File inputFile, File outputFile, int newWidth, int newHeight) throws IOException;

    // Method to convert an image to grayscale
    public File convertToGrayscale(File inputFile, File outputFile) throws IOException;

    // Method to flip an image horizontally
    public  File flipHorizontally(File inputFile, File outputFile) throws IOException;

    // Method to apply a sepia filter to an image
    public File applySepiaFilter(File inputFile, File outputFile) throws IOException;

    // Method to convert an image to a different format
    public File convertImageFormat(File inputFile, File outputFile, String outputFormat) throws IOException;
    // Method to merge two images horizontally
    public File mergeImages(List<File> inputFiles, File outputFile) throws IOException;

}