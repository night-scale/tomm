import 'package:flutter/material.dart';

class HeaderButtons extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        IconButton(
          icon: Icon(Icons.history),
          onPressed: () {
            // 处理历史按钮的点击事件
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
