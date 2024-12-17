import 'package:flutter/material.dart';
import 'package:tomm4android/screens/creation_screen.dart';
import 'package:tomm4android/screens/discover_screen.dart';
import 'package:tomm4android/screens/home_screen.dart';
import 'package:tomm4android/screens/message_screen.dart';
import 'package:tomm4android/screens/profile_screen.dart';

class BottomNavigationBarWidget extends StatefulWidget {
  @override
  _BottomNavigationBarWidgetState createState() =>
      _BottomNavigationBarWidgetState();
}

class _BottomNavigationBarWidgetState extends State<BottomNavigationBarWidget> {
  int _currentIndex = 0;

  final List<Widget> _screens = [
    HomeScreen(),
    DiscoverScreen(),
    CreationScreen(),
    MessageScreen(),
    ProfileScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _screens[_currentIndex],
      bottomNavigationBar: Container(
        height: 48, // 调低高度
        decoration: BoxDecoration(
          color: Colors.white,
          border: Border(
            top: BorderSide(color: Colors.grey.shade300, width: 0.5), // 顶部分割线
          ),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            _buildNavItem('首页', 0),
            _buildNavItem('广场', 1),
            _buildMiddleButton(), // 固定加号按钮
            _buildNavItem('消息', 3),
            _buildNavItem('我', 4),
          ],
        ),
      ),
    );
  }

  Widget _buildNavItem(String label, int index) {
    final bool isSelected = _currentIndex == index;

    return GestureDetector(
      onTap: () {
        setState(() {
          _currentIndex = index;
        });
      },
      child: Center(
        child: Text(
          label,
          style: TextStyle(
            fontSize: 14,
            fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
            color: isSelected ? Colors.red : Colors.black54,
          ),
        ),
      ),
    );
  }

  Widget _buildMiddleButton() {
    return GestureDetector(
      onTap: () {
        setState(() {
          _currentIndex = 2; // 切换到 CreationScreen
        });
      },
      child: Container(
        height: 36,
        width: 36,
        decoration: BoxDecoration(
          color: Colors.red,
          borderRadius: BorderRadius.circular(8), // 圆角按钮
        ),
        child: Center(
          child: Text(
            '+',
            style: TextStyle(
              fontSize: 20,
              color: Colors.white,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
      ),
    );
  }
}
