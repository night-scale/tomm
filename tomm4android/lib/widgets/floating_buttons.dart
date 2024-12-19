import 'package:flutter/material.dart';

class FloatingButtons extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      onPressed: () {
        // 处理发布按钮的点击事件
      },
      tooltip: '发布',
      child: Icon(Icons.add),
    );
  }
}
