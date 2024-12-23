import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:tomm4android/provider/media_provider.dart';

class PublishPage extends StatelessWidget {
  final TextEditingController titleController = TextEditingController();
  final TextEditingController descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final mediaProvider = Provider.of<MediaProvider>(context);

    return Scaffold(
      appBar: AppBar(
        title: Text("发布"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: titleController,
              decoration: InputDecoration(labelText: "添加标题"),
            ),
            SizedBox(height: 16),
            TextField(
              controller: descriptionController,
              decoration: InputDecoration(labelText: "添加正文"),
              maxLines: 4,
            ),
            SizedBox(height: 16),
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
                    return Image.file(mediaProvider.selectedImages[index], fit: BoxFit.cover);
                  } else {
                    return Center(child: Text("视频预览（实现视频播放组件）"));
                  }
                },
              ),
            ),
            ElevatedButton(
              onPressed: () {
                // 发布逻辑
                print("标题: ${titleController.text}");
                print("正文: ${descriptionController.text}");
                print("图片数量: ${mediaProvider.selectedImages.length}");
                if (mediaProvider.selectedVideo != null) {
                  print("已选择视频");
                }
              },
              child: Text("发布笔记"),
            ),
          ],
        ),
      ),
    );
  }
}
