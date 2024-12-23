
// import 'dart:typed_data';
// import 'package:flutter/material.dart';
// import 'package:permission_handler/permission_handler.dart';
// import 'package:photo_manager/photo_manager.dart';
// class CreationScreen extends StatefulWidget {
//   @override
//   _CreationScreenState createState() => _CreationScreenState();
// }
// class _CreationScreenState extends State<CreationScreen> {
//   List<AssetEntity> galleryItems = [];
//   List<AssetEntity> selectedItems = [];
//   String selectedTab = '全部'; // 默认选项是"全部"
//   @override
//   void initState() {
//     super.initState();
//     _requestPermissions();
//     _loadGalleryItems();
//   }
//   // 请求相册权限
//   Future<void> _requestPermissions() async {
//     var status = await Permission.photos.request();
//     if (status.isDenied || status.isPermanentlyDenied) {
//       openAppSettings();
//     }
//   }
//   // 加载相册内容
//   Future<void> _loadGalleryItems() async {
//     try {
//       // 获取相册权限
//       final permission = await PhotoManager.requestPermission();
//       if (!permission) {
//         ScaffoldMessenger.of(context).showSnackBar(
//             SnackBar(content: Text("相册权限未授权")));
//         return;
//       }
//       // 获取所有相册路径
//       List<AssetPathEntity> collections = await PhotoManager.getAssetPathList(onlyAll: true);
//       AssetPathEntity? assetPath = collections.isNotEmpty ? collections.first : null;
//       if (assetPath != null) {
//         // 加载相册中的图片和视频
//         final assets = await assetPath.getAssetListPaged(0, 100); // 加载前100个
//         setState(() {
//           galleryItems = assets;
//         });
//       }
//     } catch (e) {
//       print("加载相册内容失败: $e");
//     }
//   }
//   // 选择图片/视频
//   void _onSelectItem(AssetEntity item) {
//     if (selectedItems.contains(item)) {
//       setState(() {
//         selectedItems.remove(item);
//       });
//     } else {
//       if (item.type == AssetType.video) {
//         // 视频只能选择一个
//         if (selectedItems.isNotEmpty) {
//           ScaffoldMessenger.of(context).showSnackBar(SnackBar(
//             content: Text("只能选择一个视频！"),
//           ));
//           return;
//         }
//       }
//       if (selectedItems.length >= 9) {
//         ScaffoldMessenger.of(context).showSnackBar(SnackBar(
//           content: Text("最多只能选择9个文件"),
//         ));
//         return;
//       }
//       setState(() {
//         selectedItems.add(item);
//       });
//     }
//   }
//   // 删除选中的项目
//   void _onRemoveSelectedItem(int index) {
//     setState(() {
//       selectedItems.removeAt(index);
//     });
//   }
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       backgroundColor: Colors.black,
//       appBar: AppBar(
//         backgroundColor: Colors.black,
//         elevation: 0,
//         leading: IconButton(
//           icon: Icon(Icons.close, color: Colors.white),
//           onPressed: () => Navigator.pop(context),
//         ),
//       ),
//       body: Column(
//         children: [
//           // Tabs (全部/视频/照片)
//           Container(
//             color: Colors.black,
//             child: Row(
//               mainAxisAlignment: MainAxisAlignment.spaceEvenly,
//               children: ['全部', '视频', '照片']
//                   .map((tab) => GestureDetector(
//                         onTap: () {
//                           setState(() {
//                             selectedTab = tab;
//                             _loadGalleryItems();  // 每次切换Tab时重新加载
//                           });
//                         },
//                         child: Column(
//                           children: [
//                             Text(
//                               tab,
//                               style: TextStyle(
//                                 color: selectedTab == tab
//                                     ? Colors.red
//                                     : Colors.white,
//                               ),
//                             ),
//                             if (selectedTab == tab)
//                               Container(
//                                 margin: EdgeInsets.only(top: 4),
//                                 height: 2,
//                                 width: 40,
//                                 color: Colors.red,
//                               )
//                           ],
//                         ),
//                       ))
//                   .toList(),
//             ),
//           ),
//           Expanded(
//             child: GridView.builder(
//               padding: EdgeInsets.all(8.0),
//               gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
//                 crossAxisCount: 3,
//                 crossAxisSpacing: 8.0,
//                 mainAxisSpacing: 8.0,
//               ),
//               itemCount: galleryItems.length,
//               itemBuilder: (context, index) {
//                 final asset = galleryItems[index];
//                 bool isSelected = selectedItems.contains(asset);
//                 return GestureDetector(
//                   onTap: () => _onSelectItem(asset),
//                   child: Stack(
//                     children: [
//                       FutureBuilder<Uint8List?>(
//                         future: asset.thumbDataWithSize(200, 200),
//                         builder: (context, snapshot) {
//                           if (snapshot.connectionState == ConnectionState.waiting) {
//                             return Center(child: CircularProgressIndicator());
//                           }
//                           if (!snapshot.hasData) {
//                             return Container(color: Colors.grey); // No thumbnail available
//                           }
//                           return Image.memory(
//                             snapshot.data!,
//                             fit: BoxFit.cover,
//                             width: double.infinity,
//                             height: double.infinity,
//                           );
//                         },
//                       ),
//                       if (isSelected)
//                         Positioned(
//                           top: 5,
//                           right: 5,
//                           child: CircleAvatar(
//                             radius: 12,
//                             backgroundColor: Colors.red,
//                             child:
//                                 Icon(Icons.check, size: 14, color: Colors.white),
//                           ),
//                         ),
//                     ],
//                   ),
//                 );
//               },
//             ),
//           ),
//           if (selectedItems.isNotEmpty)
//             Column(
//               children: [
//                 Container(
//                   height: 80,
//                   color: Colors.black,
//                   child: ListView.builder(
//                     scrollDirection: Axis.horizontal,
//                     itemCount: selectedItems.length,
//                     itemBuilder: (context, index) {
//                       return Padding(
//                         padding: const EdgeInsets.all(8.0),
//                         child: GestureDetector(
//                           onTap: () => _onRemoveSelectedItem(index),
//                           child: Stack(
//                             children: [
//                               FutureBuilder<Uint8List?>(
//                                 future: selectedItems[index].thumbDataWithSize(60, 60),
//                                 builder: (context, snapshot) {
//                                   if (snapshot.connectionState == ConnectionState.waiting) {
//                                     return Center(child: CircularProgressIndicator());
//                                   }
//                                   if (!snapshot.hasData) {
//                                     return Container(color: Colors.grey); // No thumbnail available
//                                   }
//                                   return Image.memory(
//                                     snapshot.data!,
//                                     fit: BoxFit.cover,
//                                     width: 60,
//                                     height: 60,
//                                   );
//                                 },
//                               ),
//                               Positioned(
//                                 top: 0,
//                                 right: 0,
//                                 child: CircleAvatar(
//                                   radius: 10,
//                                   backgroundColor: Colors.red,
//                                   child: Icon(Icons.close,
//                                       size: 12, color: Colors.white),
//                                 ),
//                               ),
//                             ],
//                           ),
//                         ),
//                       );
//                     },
//                   ),
//                 ),
//                 Padding(
//                   padding: const EdgeInsets.all(8.0),
//                   child: ElevatedButton(
//                     onPressed: () {
//                       // 在此跳转到下一个页面
//                       // Navigator.push(
//                       //   context,
//                       //   MaterialPageRoute(
//                       //     builder: (context) => CreationFinalScreen(
//                       //       selectedItems: selectedItems,
//                       //     ),
//                       //   ),
//                       // );
//                     },
//                     style: ElevatedButton.styleFrom(
//                       backgroundColor: Colors.red,
//                       minimumSize: Size(double.infinity, 50),
//                     ),
//                     child: Text("下一步 (${selectedItems.length})"),
//                   ),
//                 ),
//               ],
//             ),
//         ],
//       ),
//     );
//   }
// }
// class CreationFinalScreen extends StatelessWidget {
//   final List<File> selectedItems;
//   const CreationFinalScreen({Key? key, required this.selectedItems})
//       : super(key: key);
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         leading: IconButton(
//           icon: Icon(Icons.arrow_back, color: Colors.black),
//           onPressed: () => Navigator.pop(context),
//         ),
//         backgroundColor: Colors.white,
//         elevation: 1,
//         title: Text("发布作品", style: TextStyle(color: Colors.black)),
//       ),
//       body: Column(
//         children: [
//           Container(
//             height: 100,
//             child: ListView.builder(
//               scrollDirection: Axis.horizontal,
//               itemCount: selectedItems.length,
//               itemBuilder: (context, index) {
//                 return Padding(
//                   padding: const EdgeInsets.all(8.0),
//                   child: Container(
//                     width: 80,
//                     decoration: BoxDecoration(
//                       image: DecorationImage(
//                         image: FileImage(selectedItems[index]),
//                         fit: BoxFit.cover,
//                       ),
//                     ),
//                   ),
//                 );
//               },
//             ),
//           ),
//           Padding(
//             padding: const EdgeInsets.all(8.0),
//             child: TextField(
//               decoration: InputDecoration(
//                 hintText: "添加标题",
//                 border: OutlineInputBorder(),
//               ),
//             ),
//           ),
//           Padding(
//             padding: const EdgeInsets.all(8.0),
//             child: TextField(
//               decoration: InputDecoration(
//                 hintText: "添加正文",
//                 border: OutlineInputBorder(),
//               ),
//               maxLines: 5,
//             ),
//           ),
//           Spacer(),
//           Padding(
//             padding: const EdgeInsets.all(8.0),
//             child: ElevatedButton(
//               onPressed: () {
//                 // 置空逻辑
//               },
//               style: ElevatedButton.styleFrom(
//                 backgroundColor: Colors.red,
//                 minimumSize: Size(double.infinity, 50),
//               ),
//               child: Text("发布笔记"),
//             ),
//           ),
//         ],
//       ),
//     );
//   }
// }

import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:reorderables/reorderables.dart';
import 'package:tandroid/screens/publish_screen.dart';

class MediaSelectScreen extends StatefulWidget {
  const MediaSelectScreen({super.key});

  @override
  _MediaSelectScreenState createState() => _MediaSelectScreenState();
}

class _MediaSelectScreenState extends State<MediaSelectScreen> {
  final List<Uint8List> selectedImages = []; // 存储图片数据
  final ImagePicker _picker = ImagePicker();

  @override
  void initState() {
    super.initState();
    _pickImages(); // 打开页面时直接弹出选择图片窗口
  }

  // 图片选择逻辑
  Future<void> _pickImages() async {
    try {
      final List<XFile>? pickedFiles = await _picker.pickMultiImage();
      if (pickedFiles != null) {
        final List<Uint8List> newImages = [];
        for (var file in pickedFiles) {
          newImages.add(await file.readAsBytes());
        }
        setState(() {
          selectedImages.addAll(newImages);
        });
      }
    } catch (e) {
      debugPrint('图片选择出错: $e');
    }
  }

  // 删除图片逻辑
  void _removeImage(int index) {
    setState(() {
      selectedImages.removeAt(index);
    });
  }

  // 拖动调整顺序逻辑
  void _onReorder(int oldIndex, int newIndex) {
    setState(() {
      final image = selectedImages.removeAt(oldIndex);
      selectedImages.insert(newIndex, image);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('选择图片')),
      body: Column(
        children: [
          Expanded(
            child: selectedImages.isEmpty
                ? const Center(
                    child: Text(
                      '未选择图片，点击下方按钮重新选择图片。',
                      style: TextStyle(fontSize: 16.0),
                    ),
                  )
                : ReorderableWrap(
                    spacing: 8.0,
                    runSpacing: 8.0,
                    padding: const EdgeInsets.all(8.0),
                    onReorder: _onReorder,
                    children: List.generate(selectedImages.length, (index) {
                      return Stack(
                        key: ValueKey(index),
                        children: [
                          ClipRRect(
                            borderRadius: BorderRadius.circular(8.0),
                            child: Image.memory(
                              selectedImages[index],
                              fit: BoxFit.cover,
                              width: 100.0,
                              height: 100.0,
                            ),
                          ),
                          Positioned(
                            top: 4,
                            right: 4,
                            child: GestureDetector(
                              onTap: () => _removeImage(index),
                              child: const Icon(
                                Icons.cancel,
                                color: Colors.red,
                              ),
                            ),
                          ),
                        ],
                      );
                    }),
                  ),
          ),
          // 选择图片按钮
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: ElevatedButton(
              onPressed: _pickImages,
              child: const Text('重新选择图片'),
            ),
          ),
          // 下一步按钮
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: ElevatedButton(
              onPressed: selectedImages.isNotEmpty
                  ? () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => PublishScreen(
                            // error The argument type 'List<Uint8List>' can't be assigned to the parameter type 'List<File>'. dartargument_type_not_assignable
                            selectedImages: selectedImages,
                          ),
                        ),
                      );
                    }
                  : null,
              child: const Text('下一步'),
            ),
          ),
        ],
      ),
    );
  }
}
