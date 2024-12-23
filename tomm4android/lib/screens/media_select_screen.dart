
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
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';

import 'package:tomm4android/provider/media_provider.dart'; // 导入状态管理类
import 'package:tomm4android/screens/publish_page.dart';
class MediaSelectionScreen extends StatelessWidget {
  final ImagePicker _picker = ImagePicker();

  Future<void> _pickImages(BuildContext context) async {
    final List<XFile> images = await _picker.pickMultiImage();
    final mediaProvider = Provider.of<MediaProvider>(context, listen: false);
    for (var image in images) {
      mediaProvider.addImage(File(image.path));
    }
    }

  Future<void> _pickVideo(BuildContext context) async {
    final XFile? video = await _picker.pickVideo(source: ImageSource.gallery);
    if (video != null) {
      Provider.of<MediaProvider>(context, listen: false).setVideo(File(video.path));
    }
  }

  @override
  Widget build(BuildContext context) {
    final mediaProvider = Provider.of<MediaProvider>(context);

    return Scaffold(
      appBar: AppBar(
        title: Text("选择图片或视频"),
      ),
      body: Column(
        children: [
          Expanded(
            child: GridView.builder(
              gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                crossAxisSpacing: 4,
                mainAxisSpacing: 4,
              ),
              itemCount: mediaProvider.selectedImages.length +
                  (mediaProvider.selectedVideo == null ? 0 : 1),
              itemBuilder: (context, index) {
                if (index < mediaProvider.selectedImages.length) {
                  return Stack(
                    children: [
                      Image.file(mediaProvider.selectedImages[index], fit: BoxFit.cover),
                      Positioned(
                        top: 0,
                        right: 0,
                        child: IconButton(
                          icon: Icon(Icons.close, color: Colors.red),
                          onPressed: () => mediaProvider.removeImage(index),
                        ),
                      ),
                    ],
                  );
                } else {
                  return Stack(
                    children: [
                      VideoPlayerWidget(file: mediaProvider.selectedVideo!),
                      Positioned(
                        top: 0,
                        right: 0,
                        child: IconButton(
                          icon: Icon(Icons.close, color: Colors.red),
                          onPressed: () => mediaProvider.clearMedia(),
                        ),
                      ),
                    ],
                  );
                }
              },
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              ElevatedButton(
                onPressed: () => _pickImages(context),
                child: Text("选择图片"),
              ),
              ElevatedButton(
                onPressed: () => _pickVideo(context),
                child: Text("选择视频"),
              ),
            ],
          ),
          SizedBox(height: 16),
          ElevatedButton(
            onPressed: () {
              if (mediaProvider.selectedImages.isNotEmpty || mediaProvider.selectedVideo != null) {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (_) => PublishPage()),
                );
              }
            },
            child: Text("下一步"),
          ),
        ],
      ),
    );
  }
}

class VideoPlayerWidget extends StatelessWidget {
  final File file;

  VideoPlayerWidget({required this.file});

  @override
  Widget build(BuildContext context) {
    return Center(child: Text("视频预览（实现视频播放组件）"));
  }
}
