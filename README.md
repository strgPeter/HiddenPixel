# HiddenPixel

HiddenPixel is a steganography application that enables you to hide secret messages within images. Using the Least Significant Bit (LSB) technique, it encodes text into the color channels of image pixels, making the changes virtually imperceptible to the human eye.

## Features

- **Message Encoding**: Hide text messages within PNG images
- **Message Decoding**: Extract hidden messages from encoded images
- **Channel Selection**: Choose which color channels (Alpha, Red, Green, Blue) to use for encoding
- **Visual Interface**: User-friendly JavaFX interface for all operations
- **Image Saving**: Save your encoded images for sharing

## Technologies

- Kotlin
- JavaFX
- Maven

## Installation

1. Clone the repository
2. Ensure you have JDK 11+ installed
3. Build with Maven:
```
mvn clean package
```
4. Run the application:
```
java -jar target/hiddenpixel-1.0.jar
```

## How to Use

1. **Load an Image**: Click "Load Image" to select a PNG file
2. **Select Channels**: Choose which color channels to use (Alpha, Red, Green, Blue)
3. **Encode Mode**:
    - Enter your secret message in the text field
    - Click "Encode" to hide the message in the image
    - Save the result with "Save Image"
4. **Decode Mode**:
    - Load an image with a hidden message
    - Select the same channels used for encoding
    - Click "Decode" to extract the hidden message

## How It Works

HiddenPixel uses steganography to hide information by replacing the least significant bit (LSB) of selected color channels in each pixel. This causes minimal visual change to the image while storing binary data that can later be extracted.

The application also encodes the message length in the first row of pixels to enable accurate decoding.