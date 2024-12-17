import 'package:flutter/material.dart';
import 'package:tomm4android/widgets/top_buttons.dart';

class MessageScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('消息'),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          TopButtons(), // 顶部手势按钮区域
          // 其他消息页面内容
          Center(
            child: Text('这里是消息页面的内容'),
          ),
        ],
      ),
    );
  }
}
