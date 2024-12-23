import 'dart:io';
import 'package:flutter/material.dart';

class MediaProvider with ChangeNotifier {
  List<File> selectedImages = [];
  File? selectedVideo;

  void addImage(File image) {
    if (selectedImages.length < 9) {
      selectedImages.add(image);
      notifyListeners();
    }
  }

  void setVideo(File video) {
    selectedImages.clear(); // 清除图片
    selectedVideo = video;
    notifyListeners();
  }

  void removeImage(int index) {
    selectedImages.removeAt(index);
    notifyListeners();
  }

  void clearMedia() {
    selectedImages.clear();
    selectedVideo = null;
    notifyListeners();
  }
}
