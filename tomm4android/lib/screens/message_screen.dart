
import 'package:flutter/material.dart';

class MessageScreen extends StatelessWidget {
  final List<Message> messages = [
    Message("邀请回答", "推荐问题邀你回答", "12-15", Icons.question_answer, Colors.blue),
    Message("官方账号消息", "亲爱的 kickoutjam：恭喜您获得「25软考通关营」", "12-15", Icons.verified, Colors.blue),
    Message("创作者小助手", "亲爱的 kickoutjam：你的上周创作周报（11.25 至...", "12-03", Icons.person, Colors.purple),
    Message("系统消息", "内容违规处理通知", "07-20", Icons.notifications, Colors.grey),
    Message("长啥样", "你发了啥呀被删了", "02-05", Icons.pets, Colors.blueAccent),
    Message("超赞包小助手", "超赞包助力上热门，新人福利来啦~首次购买「...", "01-31", Icons.thumb_up, Colors.orange),
    Message("知乎活动助手", "发想法，得盐粒！", "2023-11-11", Icons.lightbulb, Colors.orangeAccent),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("消息"),
        centerTitle: true,
        actions: [
          IconButton(
            icon: Icon(Icons.add_alert),
            onPressed: () {},
          ),
          IconButton(
            icon: Icon(Icons.palette),
            onPressed: () {},
          ),
        ],
      ),
      body: Column(
        children: [
          // 顶部图标菜单
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 10),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                _buildTopMenuItem(
                    context, Icons.chat_bubble_outline, "评论转发", "评论转发详情"),
                _buildTopMenuItem(
                    context, Icons.favorite_border, "赞同喜欢", "赞同喜欢详情"),
                _buildTopMenuItem(
                    context, Icons.star_border, "收藏了我", "收藏了我详情"),
                _buildTopMenuItem(
                    context, Icons.visibility_outlined, "关注订阅", "关注订阅详情"),
              ],
            ),
          ),
          Divider(height: 1, color: Colors.grey[300]),
          // 消息列表
          Expanded(
            child: ListView.builder(
              itemCount: messages.length,
              itemBuilder: (context, index) {
                final message = messages[index];
                return ListTile(
                  leading: CircleAvatar(
                    backgroundColor: message.iconColor,
                    child: Icon(message.icon, color: Colors.white),
                  ),
                  title: Text(message.title),
                  subtitle: Text(message.subtitle, overflow: TextOverflow.ellipsis),
                  trailing: Text(message.date, style: TextStyle(color: Colors.grey)),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => MessageDetailScreen(message: message),
                      ),
                    );
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTopMenuItem(
      BuildContext context, IconData icon, String label, String detailTitle) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => ListDetailScreen(title: detailTitle),
          ),
        );
      },
      child: Column(
        children: [
          Icon(icon, size: 28),
          SizedBox(height: 5),
          Text(label, style: TextStyle(fontSize: 12)),
        ],
      ),
    );
  }
}

class MessageDetailScreen extends StatelessWidget {
  final Message message;

  const MessageDetailScreen({super.key, required this.message});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(message.title),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                CircleAvatar(
                  backgroundColor: message.iconColor,
                  child: Icon(message.icon, color: Colors.white),
                ),
                SizedBox(width: 10),
                Text(
                  message.title,
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
              ],
            ),
            SizedBox(height: 20),
            Text(
              message.subtitle,
              style: TextStyle(fontSize: 16),
            ),
            Spacer(),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                IconButton(
                  icon: Icon(Icons.comment),
                  onPressed: () {},
                ),
                IconButton(
                  icon: Icon(Icons.thumb_up),
                  onPressed: () {},
                ),
                IconButton(
                  icon: Icon(Icons.bookmark),
                  onPressed: () {},
                ),
                IconButton(
                  icon: Icon(Icons.share),
                  onPressed: () {},
                ),
              ],
            )
          ],
        ),
      ),
    );
  }
}

class ListDetailScreen extends StatelessWidget {
  final String title;

  const ListDetailScreen({super.key, required this.title});

  @override
  Widget build(BuildContext context) {
    final List<String> items = List.generate(
      10,
      (index) => "$title 第 ${index + 1} 条内容",
    );

    return Scaffold(
      appBar: AppBar(
        title: Text(title),
        centerTitle: true,
      ),
      body: ListView.builder(
        itemCount: items.length,
        itemBuilder: (context, index) {
          return ListTile(
            leading: CircleAvatar(
              backgroundColor: Colors.blueAccent,
              child: Text(
                (index + 1).toString(),
                style: TextStyle(color: Colors.white),
              ),
            ),
            title: Text(items[index]),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => DetailItemScreen(item: items[index]),
                ),
              );
            },
          );
        },
      ),
    );
  }
}

class DetailItemScreen extends StatelessWidget {
  final String item;

  const DetailItemScreen({super.key, required this.item});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("详情"),
        centerTitle: true,
      ),
      body: Center(
        child: Text(
          item,
          style: TextStyle(fontSize: 20),
        ),
      ),
    );
  }
}

class Message {
  final String title;
  final String subtitle;
  final String date;
  final IconData icon;
  final Color iconColor;

  Message(this.title, this.subtitle, this.date, this.icon, this.iconColor);
}

void main() => runApp(MaterialApp(
      home: MessageScreen(),
    ));
