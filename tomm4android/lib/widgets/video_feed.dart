import 'package:flutter/material.dart';
import 'package:tomm4android/models/video.dart'; // 假设有视频数据模型

class VideoFeed extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // 假设有一个视频列表，可以根据需要自定义实现
    List<Video> videos = [
      Video(title: "title1", description: 'sss', videoUrl: 'fsd', thumbnailUrl: 'fsdf', ),
      // 其他视频数据
    ];

    return ListView.builder(
      itemCount: videos.length,
      itemBuilder: (context, index) {
        Video video = videos[index];
        return ListTile(
          title: Text(video.title),
          subtitle: Text(video.description),
          // 可以根据视频URL显示视频内容或封面图
          leading: CircleAvatar(
            backgroundImage: NetworkImage(video.thumbnailUrl),
          ),
          onTap: () {
            // 处理点击视频的事件
          },
        );
      },
    );
  }
}
