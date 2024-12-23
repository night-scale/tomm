import 'dart:typed_data';
import 'package:flutter/material.dart';

class PublishScreen extends StatelessWidget {
  final List<Uint8List> selectedImages; // 修改为 List<Uint8List>

  const PublishScreen({super.key, required this.selectedImages});

  @override
  Widget build(BuildContext context) {
    final TextEditingController titleController = TextEditingController();
    final TextEditingController contentController = TextEditingController();

    return Scaffold(
      appBar: AppBar(title: const Text('发布笔记')),
      body: Column(
        children: [
          // 图片预览区域
          SizedBox(
            height: 100.0,
            child: ListView.builder(
              scrollDirection: Axis.horizontal,
              itemCount: selectedImages.length,
              itemBuilder: (context, index) {
                return Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8.0),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(8.0),
                    child: Image.memory(
                      selectedImages[index], // 显示 Uint8List 图片
                      fit: BoxFit.cover,
                      width: 100.0,
                      height: 100.0,
                    ),
                  ),
                );
              },
            ),
          ),
          const Divider(),
          // 标题输入框
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              controller: titleController,
              decoration: const InputDecoration(
                hintText: '输入标题',
                border: OutlineInputBorder(),
              ),
            ),
          ),
          // 正文输入框
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: TextField(
              controller: contentController,
              maxLines: 5,
              decoration: const InputDecoration(
                hintText: '输入正文内容',
                border: OutlineInputBorder(),
              ),
            ),
          ),
          const Spacer(),
          // 发布按钮
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: ElevatedButton(
              onPressed: () {
                final title = titleController.text;
                final content = contentController.text;

                if (title.isEmpty || content.isEmpty) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('标题和正文不能为空！')),
                  );
                } else {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('发布成功！')),
                  );
                  Navigator.pop(context);
                }
              },
              child: const Text('发布'),
            ),
          ),
        ],
      ),
    );
  }
}
