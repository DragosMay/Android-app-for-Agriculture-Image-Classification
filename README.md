# 📱 Fruit & Plant Classifier

An Android application that leverages multiple TensorFlow Lite models for accurate classification of fruits and plant diseases. Built with Java and Android SDK, this app provides offline image classification using state-of-the-art deep learning architectures.

## 🚀 Features

- **🖼️ Image Selection**: Choose images from device gallery
- **🧠 Multi-Model Architecture**: 6 specialized CNN models for optimal accuracy
- **🎯 Dual Classification**: Separate models for fruits and plant diseases
- **⚡ Performance Tracking**: Real-time inference time measurement
- **💾 Offline Operation**: Complete functionality without internet connection
- **📊 Detailed Results**: Confidence scores and processing metrics

## 🛠️ Technologies Used

- **Java** - Primary development language
- **Android SDK** - Mobile application framework
- **TensorFlow Lite (LiteRT)** - Machine learning inference
- **ImageView** - Image display and processing
- **Gallery Integration** - Image selection from device storage

## 🧠 Model Architecture

### Fruit Classification Models
| Model | Description | Optimization |
|-------|-------------|--------------|
| **VCNN** | Custom Vision CNN | Fruit-specific feature extraction |
| **EfficientNet** | Efficient neural architecture | Balanced accuracy and speed |
| **MobileNet** | Lightweight CNN | Mobile-optimized inference |

### Plant Disease Detection Models
| Model | Description | Specialization |
|-------|-------------|----------------|
| **VCNN** | Vision CNN for plants | Plant pathology detection |
| **VRes-CNN** | Residual CNN architecture | Complex disease pattern recognition |
| **EfficientNet** | Efficient neural network | Plant disease classification |

## 📂 Project Structure

```
├── app/
│   ├── src/main/java/          # Java source code
│   ├── src/main/res/           # UI resources
│   └── src/main/assets/        # TFLite models
├── assets/
│   ├── fruit_vcnn.tflite       # Fruit VCNN model
│   ├── fruit_efficientnet.tflite
│   ├── fruit_mobilenet.tflite
│   ├── plant_vcnn.tflite       # Plant VCNN model
│   ├── plant_vres_cnn.tflite
│   └── plant_efficientnet.tflite
└── README.md
```

## 🔧 Setup & Installation

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/fruit-plant-classifier.git
   cd fruit-plant-classifier
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Add TensorFlow Lite Models**
   - Place all 6 `.tflite` model files in `app/src/main/assets/`
   - Ensure models are named correctly as per the code references

4. **Configure Permissions**
   - Verify storage permissions in `AndroidManifest.xml`
   ```xml
   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
   ```

5. **Build and Run**
   - Connect Android device or start emulator
   - Click "Run" or use `Ctrl+R`

## 📱 How to Use

1. **Launch App** - Open the Fruit & Plant Classifier application
2. **Select Image** - Tap the "Select" button to choose an image from gallery
3. **Choose Model Type** - Select between fruit classification or plant disease detection
4. **Predict** - Hit the "Predict" button to run inference
5. **View Results** - See the classification result, confidence score, and processing time

## 📊 Output Information

The app provides comprehensive results for each classification:

- **📷 Classified Image**: Visual display of the selected image
- **🏷️ Prediction Result**: Identified fruit type or plant disease
- **📈 Confidence Score**: Accuracy percentage (0-100%)
- **⏱️ Processing Time**: Model inference duration in milliseconds

## 🎯 Supported Classifications

### Fruits
- Apples, Bananas, Oranges, Grapes
- Strawberries, Pineapples, Mangoes
- And many more varieties...

### Plant Diseases
- Leaf spot diseases
- Bacterial infections
- Fungal pathogens
- Nutrient deficiencies
- Viral infections

## 🔍 Technical Details

### Model Performance
- **Inference Time**: 50-200ms depending on model and device
- **Model Size**: 5-25MB per model
- **Accuracy**: 85-95% on test datasets
- **Supported Formats**: RGB images, JPEG/PNG

### Device Requirements
- **Android Version**: 5.0 (API 21) or higher
- **RAM**: Minimum 2GB recommended
- **Storage**: 200MB free space for models
- **Processor**: ARM or x86 architecture

## 📄 License

Maydanskyy Dragos-Laurentiu

## 📞 Contact

**Project Maintainer**: [Dragos]
- Email: maydanskyyd@gmail.com

## 📈 Future Enhancements

- [ ] Real-time camera integration
- [ ] Batch image processing
- [ ] Cloud model updates
- [ ] Multi-language support
- [ ] Export classification results
- [ ] Integration with agricultural databases

---

**🍃 Portable AI-Powered Classification – Identifying fruits and plant diseases with precision and speed** 🍎🌿

*Perfect for agricultural applications, educational purposes, and botanical research*