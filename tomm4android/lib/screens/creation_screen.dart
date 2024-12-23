// 请根据这两张效果图创建flutter屏幕，需求如下：
// 1. 在第一个creation_screen中，打开后读取系统相册，分为全部/视频/照片三类，用户可自由选择最多九张照片或一个视频，
// 左上角有一个关闭按钮，选择后下方弹出一个小窗，可以查看或拖动修改顺序/删除选中，小窗下方是“下一步”，这将会打开creation_final_screen
// 2. 在creation_final_screen中，上方展示选中的视频/照片的缩略图。下面可以添加标题/作品描述，最下方有发送按钮，该按钮暂时置空。左上角有返回箭头键
import 'package:flutter/material.dart';

class CreationScreen extends StatefulWidget {
  @override
  _CreationScreenState createState() => _CreationScreenState();
}

class _CreationScreenState extends State<CreationScreen> {
  List<String> selectedItems = []; // 模拟已选择的照片/视频

  void openGallery() {
    // 模拟打开系统相册
    print("系统相册打开");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      appBar: AppBar(
        backgroundColor: Colors.black,
        elevation: 0,
        leading: IconButton(
          icon: Icon(Icons.close, color: Colors.white),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: Text(
          "全部",
          style: TextStyle(color: Colors.white),
        ),
        actions: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Icon(Icons.settings, color: Colors.white),
          ),
        ],
      ),
      body: Column(
        children: [
          // TabBar - 全部/视频/照片
          Container(
            color: Colors.black,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                _buildTab("全部", true),
                _buildTab("视频", false),
                _buildTab("照片", false),
              ],
            ),
          ),
          // 主内容
          Expanded(
            child: GridView.builder(
              padding: EdgeInsets.all(8.0),
              gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                crossAxisSpacing: 8.0,
                mainAxisSpacing: 8.0,
              ),
              itemCount: 20, // 模拟20张图片
              itemBuilder: (context, index) {
                return GestureDetector(
                  onTap: () {
                    setState(() {
                      if (selectedItems.contains("Item $index")) {
                        selectedItems.remove("Item $index");
                      } else if (selectedItems.length < 9) {
                        selectedItems.add("Item $index");
                      }
                    });
                  },
                  child: Stack(
                    children: [
                      Container(
                        color: Colors.grey[300],
                        child: Center(
                          child: Text(
                            "图片$index",
                            style: TextStyle(color: Colors.black),
                          ),
                        ),
                      ),
                      if (selectedItems.contains("Item $index"))
                        Positioned(
                          top: 5,
                          right: 5,
                          child: CircleAvatar(
                            radius: 12,
                            backgroundColor: Colors.red,
                            child: Text(
                              "${selectedItems.indexOf("Item $index") + 1}",
                              style: TextStyle(color: Colors.white, fontSize: 12),
                            ),
                          ),
                        )
                    ],
                  ),
                );
              },
            ),
          ),
          // 底部选择预览窗口
          if (selectedItems.isNotEmpty)
            Column(
              children: [
                Container(
                  height: 80,
                  color: Colors.black,
                  child: ListView.builder(
                    scrollDirection: Axis.horizontal,
                    itemCount: selectedItems.length,
                    itemBuilder: (context, index) {
                      return Stack(
                        children: [
                          Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Container(
                              width: 60,
                              color: Colors.grey[300],
                              child: Center(
                                child: Text(
                                  selectedItems[index],
                                  style: TextStyle(fontSize: 10),
                                ),
                              ),
                            ),
                          ),
                          Positioned(
                            top: 0,
                            right: 0,
                            child: GestureDetector(
                              onTap: () {
                                setState(() {
                                  selectedItems.removeAt(index);
                                });
                              },
                              child: CircleAvatar(
                                radius: 10,
                                backgroundColor: Colors.black,
                                child: Icon(Icons.close, size: 14, color: Colors.red),
                              ),
                            ),
                          ),
                        ],
                      );
                    },
                  ),
                ),
                // 下一步按钮
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => CreationFinalScreen(selectedItems: selectedItems)),
                      );
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.red,
                      minimumSize: Size(double.infinity, 50),
                    ),
                    child: Text("下一步(${selectedItems.length})"),
                  ),
                ),
              ],
            ),
        ],
      ),
    );
  }

  Widget _buildTab(String label, bool isSelected) {
    return Column(
      children: [
        Text(
          label,
          style: TextStyle(color: isSelected ? Colors.red : Colors.white),
        ),
        if (isSelected)
          Container(
            width: 40,
            height: 2,
            color: Colors.red,
          )
      ],
    );
  }
}
class CreationFinalScreen extends StatelessWidget {
  final List<String> selectedItems;

  CreationFinalScreen({required this.selectedItems});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: IconButton(
          icon: Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: Text(
          "编辑",
          style: TextStyle(color: Colors.black),
        ),
      ),
      body: Column(
        children: [
          // 上方缩略图展示
          Container(
            height: 100,
            color: Colors.grey[200],
            child: ListView.builder(
              scrollDirection: Axis.horizontal,
              itemCount: selectedItems.length,
              itemBuilder: (context, index) {
                return Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Container(
                    width: 80,
                    color: Colors.grey[300],
                    child: Center(
                      child: Text(
                        selectedItems[index],
                        style: TextStyle(fontSize: 10),
                      ),
                    ),
                  ),
                );
              },
            ),
          ),
          // 添加标题和描述
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              decoration: InputDecoration(
                hintText: "添加标题",
                border: OutlineInputBorder(),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              maxLines: 5,
              decoration: InputDecoration(
                hintText: "添加正文",
                border: OutlineInputBorder(),
              ),
            ),
          ),
          Spacer(),
          // 底部按钮
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: ElevatedButton(
              onPressed: () {
                // 暂时置空
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red,
                minimumSize: Size(double.infinity, 50),
              ),
              child: Text("发布笔记"),
            ),
          ),
        ],
      ),
    );
  }
}
