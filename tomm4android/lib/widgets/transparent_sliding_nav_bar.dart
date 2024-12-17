import 'package:flutter/material.dart';
import 'package:tomm4android/widgets/contents/grid_content.dart';
import 'package:tomm4android/widgets/contents/video_content.dart';

class TransparentSlidingNavbar extends StatefulWidget {
  @override
  _TransparentSlidingNavbarState createState() => _TransparentSlidingNavbarState();
}

class _TransparentSlidingNavbarState extends State<TransparentSlidingNavbar>
    with SingleTickerProviderStateMixin {
  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    // 启动时从第二项开始
    _tabController = TabController(length: 3, vsync: this, initialIndex: 1);
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

    @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          // TabBarView 显示内容
          TabBarView(
            controller: _tabController,
            children: [
              // 示例内容
              GridContent(title: 'form'), // 这里是你的视频内容组件
              VideoPage(), // 示例页面
              Container(color: Colors.green), // 示例页面
            ],
          ),
          // 浮动在上方的 TabBar
          SafeArea(
            child: Align(
              alignment: Alignment.topCenter, // 浮动在顶部
              child: Container(
                color: Colors.transparent, // 透明背景
                child: TabBar(
                  controller: _tabController,
                  indicatorColor: Colors.red,
                  labelColor: Colors.white,
                  unselectedLabelColor: Colors.grey.shade300,
                  labelStyle: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                  unselectedLabelStyle: TextStyle(fontSize: 14),
                  tabs: [
                    Tab(text: '关注'),
                    Tab(text: '推荐'),
                    Tab(text: '热门'),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

