import 'package:flutter/material.dart';

class CreationScreen extends StatefulWidget {
  @override
  _CreationScreenState createState() => _CreationScreenState();
}

class _CreationScreenState extends State<CreationScreen> {
  List<String> selectedPhotos = []; // 选中的照片
  String? selectedVideo; // 选中的视频

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: IconButton(
          icon: Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () {
            Navigator.pop(context); // 返回上一个页面
          },
        ),
        title: Text(
          '创作工具',
          style: TextStyle(color: Colors.black),
        ),
        centerTitle: true,
      ),
      body: Column(
        children: [
          Expanded(
            child: GridView.builder(
              padding: EdgeInsets.all(10),
              gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: 20, // 假设有20张图片/视频可供选择
              itemBuilder: (context, index) {
                bool isVideo = index % 5 == 0; // 假设每5个是视频
                String item = isVideo ? "视频$index" : "照片$index";
                bool isSelected = isVideo
                    ? selectedVideo == item
                    : selectedPhotos.contains(item);

                return GestureDetector(
                  onTap: () {
                    setState(() {
                      if (isVideo) {
                        // 视频只能单选
                        if (selectedVideo == item) {
                          selectedVideo = null; // 取消选中
                        } else {
                          selectedVideo = item;
                          selectedPhotos.clear(); // 清空照片选择
                        }
                      } else {
                        // 照片可以多选
                        if (selectedPhotos.contains(item)) {
                          selectedPhotos.remove(item); // 取消选中
                        } else {
                          selectedPhotos.add(item);
                          selectedVideo = null; // 清空视频选择
                        }
                      }
                    });
                  },
                  child: Container(
                    decoration: BoxDecoration(
                      color: isSelected ? Colors.blue : Colors.grey[300],
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Center(
                      child: Text(
                        item,
                        style: TextStyle(
                          color: isSelected ? Colors.white : Colors.black,
                        ),
                      ),
                    ),
                  ),
                );
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: ElevatedButton(
              onPressed: selectedPhotos.isNotEmpty || selectedVideo != null
                  ? () {
                      // 跳转到最终界面
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => CreationFinalScreen(
                            photos: selectedPhotos,
                            video: selectedVideo,
                          ),
                        ),
                      );
                    }
                  : null,
              child: Text('下一步'),
            ),
          ),
        ],
      ),
    );
  }
}

class CreationFinalScreen extends StatefulWidget {
  final List<String> photos; // 传递的照片列表
  final String? video; // 传递的视频

  const CreationFinalScreen({required this.photos, this.video});

  @override
  _CreationFinalScreenState createState() => _CreationFinalScreenState();
}

class _CreationFinalScreenState extends State<CreationFinalScreen> {
  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: IconButton(
          icon: Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () {
            Navigator.pop(context); // 返回上一个页面
          },
        ),
        title: Text(
          '完成创作',
          style: TextStyle(color: Colors.black),
        ),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                '已选中内容',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              SizedBox(height: 10),
              if (widget.video != null)
                Container(
                  height: 100,
                  color: Colors.grey[300],
                  child: Center(
                    child: Text(widget.video!),
                  ),
                ),
              if (widget.photos.isNotEmpty)
                Wrap(
                  spacing: 10,
                  runSpacing: 10,
                  children: widget.photos
                      .map(
                        (photo) => Container(
                          height: 100,
                          width: 100,
                          color: Colors.grey[300],
                          child: Center(
                            child: Text(photo),
                          ),
                        ),
                      )
                      .toList(),
                ),
              SizedBox(height: 20),
              TextField(
                controller: _titleController,
                decoration: InputDecoration(
                  labelText: '标题',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 20),
              TextField(
                controller: _descriptionController,
                decoration: InputDecoration(
                  labelText: '描述',
                  border: OutlineInputBorder(),
                ),
                maxLines: 5,
              ),
              SizedBox(height: 20),
              Row(
                children: [
                  Expanded(
                    child: ElevatedButton(
                      onPressed: () {
                        // 模拟发送功能
                        String title = _titleController.text;
                        String description = _descriptionController.text;

                        if (title.isEmpty || description.isEmpty) {
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(content: Text('请填写完整标题和描述')),
                          );
                          return;
                        }

                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(content: Text('创作已提交！')),
                        );
                        Navigator.pop(context); // 返回上一页面
                      },
                      child: Text('发送'),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
