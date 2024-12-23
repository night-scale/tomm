import 'package:flutter/material.dart';


class GridContent extends StatelessWidget {
  const GridContent({
    super.key,
    required this.title,
  });

  final String title;

  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: [
        SliverPadding(
          padding: const EdgeInsets.all(10),
          sliver: SliverGrid(
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2, // 两列
              crossAxisSpacing: 10, // 列间距
              mainAxisSpacing: 10, // 行间距
              childAspectRatio: 0.7, // 每个子项的宽高比
            ),
            delegate: SliverChildBuilderDelegate(
              (context, index) {
                return Container(
                  decoration: BoxDecoration(
                    color: Colors.grey[200],
                    borderRadius: BorderRadius.circular(10),
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Icon(Icons.image, size: 60, color: Colors.grey),
                      SizedBox(height: 10),
                      Text('$title 内容 $index',
                          style: TextStyle(fontSize: 14)),
                    ],
                  ),
                );
              },
              childCount: 20, // 每个Tab页面显示20个项目
            ),
          ),
        ),
      ],
    );
  }
}