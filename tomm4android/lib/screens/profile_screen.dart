import 'package:flutter/material.dart';

class ProfileScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('我的'),
        actions: [
          IconButton(
            icon: Icon(Icons.settings),
            onPressed: () {
              // 跳转到设置页面
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => SettingsScreen()),
              );
            },
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // 用户信息
            _buildProfileInfo(),
            SizedBox(height: 20),
            // 常用操作按钮
            _buildActionButtons(),
            SizedBox(height: 20),
            // 其他功能（例如历史记录、收藏等）
            _buildOtherFeatures(),
          ],
        ),
      ),
    );
  }

  // 用户信息部分
  Widget _buildProfileInfo() {
    return Row(
      children: [
        CircleAvatar(
          radius: 50,
          backgroundImage: AssetImage('assets/images/profile_picture.jpg'), // 用户头像图片
        ),
        SizedBox(width: 20),
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '用户名', // 可以通过API获取用户名
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            Text(
              '@username', // 用户的昵称或账号
              style: TextStyle(fontSize: 16, color: Colors.grey),
            ),
            SizedBox(height: 10),
            ElevatedButton(
              onPressed: () {
                // 编辑个人资料功能
              },
              child: Text('编辑资料'),
            ),
          ],
        ),
      ],
    );
  }

  // 常用操作按钮
  Widget _buildActionButtons() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        ListTile(
          leading: Icon(Icons.favorite_border),
          title: Text('收藏'),
          onTap: () {
            // 跳转到收藏页面
          },
        ),
        ListTile(
          leading: Icon(Icons.lock),
          title: Text('隐私设置'),
          onTap: () {
            // 跳转到隐私设置页面
          },
        ),
        ListTile(
          leading: Icon(Icons.notifications),
          title: Text('通知设置'),
          onTap: () {
            // 跳转到通知设置页面
          },
        ),
      ],
    );
  }

  // 其他功能部分（例如历史记录、好友管理等）
  Widget _buildOtherFeatures() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        ListTile(
          leading: Icon(Icons.history),
          title: Text('观看历史'),
          onTap: () {
            // 跳转到观看历史页面
          },
        ),
        ListTile(
          leading: Icon(Icons.people),
          title: Text('好友管理'),
          onTap: () {
            // 跳转到好友管理页面
          },
        ),
      ],
    );
  }
}

// 设置页面的占位符
class SettingsScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('设置')),
      body: Center(
        child: Text('这里是设置页面'),
      ),
    );
  }
}
