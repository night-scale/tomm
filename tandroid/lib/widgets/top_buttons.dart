import 'package:flutter/material.dart';

class TopButtons extends StatelessWidget {
  const TopButtons({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: [
        IconButton(
          icon: Icon(Icons.history),
          onPressed: () {
            // 处理历史按钮的点击事件
          },
        ),
        IconButton(
          icon: Icon(Icons.favorite),
          onPressed: () {
            // 处理喜欢按钮的点击事件
          },
        ),
        IconButton(
          icon: Icon(Icons.search),
          onPressed: () {
            // 处理搜索按钮的点击事件
          },
        ),
      ],
    );
  }
}
