import 'package:flutter/material.dart';

class FloatingButtons extends StatelessWidget {
  const FloatingButtons({super.key});

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
