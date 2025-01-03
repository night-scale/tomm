import 'package:flutter/material.dart';
import 'package:tandroid/widgets/header_buttons.dart';
import 'package:tandroid/widgets/home/transparent_sliding_nav_bar.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

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
