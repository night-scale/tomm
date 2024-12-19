import 'package:flutter/material.dart';
import 'package:tomm4android/widgets/header_buttons.dart';
import 'package:tomm4android/widgets/home/transparent_sliding_nav_bar.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          SafeArea(child: TransparentSlidingNavbar()),

          SafeArea(child: HeaderButtons()),
        ],
      ),
    );
  }
}
