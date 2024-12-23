import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoPage extends StatelessWidget {
  final List<String> videoUrls = [
    'https://www.example.com/video1.mp4', // 替换为实际视频URL
    'https://www.example.com/video2.mp4',
    'https://www.example.com/video3.mp4',
  ];
  // TODO 开发用本地视频
  final List<String> assets = [
    '2',
    '3',
    '1'
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView.builder(
        scrollDirection: Axis.vertical, // 上下滑动
        itemCount: assets.length,
        itemBuilder: (context, index) {
          return VideoPlayerScreen(url: assets[index]);
        },
      ),
    );
  }
}

class VideoPlayerScreen extends StatefulWidget {
  final String url;

  const VideoPlayerScreen({super.key, required this.url});

  @override
  _VideoPlayerScreenState createState() => _VideoPlayerScreenState();
}

class _VideoPlayerScreenState extends State<VideoPlayerScreen> {
  late VideoPlayerController _controller;

  @override
  void initState() {
    super.initState();
    // TODO only in development
    var s = 'assets/videos/${widget.url}.mp4';
    
    _controller = VideoPlayerController.asset(s)
      ..initialize().then((_) {
        setState(() {});
        _controller.play(); // 自动播放视频
        _controller.setLooping(true); // 循环播放
      });
  }

  @override
  void dispose() {
    _controller.dispose(); // 释放资源
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: _controller.value.isInitialized
          ? AspectRatio(
              aspectRatio: _controller.value.aspectRatio,
              child: VideoPlayer(_controller),
            )
          : CircularProgressIndicator(),
    );
  }
}