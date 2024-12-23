import 'package:flutter/material.dart';
import 'package:tandroid/widgets/home/grid_content.dart';
import 'package:tandroid/widgets/home/video_content.dart';

class TransparentSlidingNavbar extends StatefulWidget {
  const TransparentSlidingNavbar({super.key});

  @override
  _TransparentSlidingNavbarState createState() =>
      _TransparentSlidingNavbarState();
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
          TabBarView(
            controller: _tabController,
            children: [
              GridContent(title: 'form'),
              VideoPage(),
              Container(color: Colors.green),
            ],
          ),
          SafeArea(
            child: Align(
              alignment: Alignment.topCenter,
              child: Container(
                color: Colors.transparent,
                child: TabBar(
                  controller: _tabController,
                  indicatorColor: Colors.red,
                  labelColor: Colors.white,
                  unselectedLabelColor: Colors.grey.shade300,
                  labelStyle: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                  unselectedLabelStyle: TextStyle(fontSize: 14),
                  labelPadding: const EdgeInsets.symmetric(horizontal: 6.0), // TODO 更小的按钮间距
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
