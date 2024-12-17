import 'package:flutter/material.dart';
import 'package:tomm4android/widgets/top_buttons.dart';
import 'package:tomm4android/widgets/video_feed.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('首页'),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          TopButtons(), // 顶部浮动按钮区域
          Expanded(
            child: VideoFeed(), // 视频展示区域
          ),
        ],
      ),
    );
  }
}
