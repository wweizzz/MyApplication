import 'package:flutter/material.dart';

/// WillPopScope 导航返回拦截
/// 每个 WillPopScope 仅对当前组件生效，不可在 MaterialApp 上使用。
class MyPopScope extends StatelessWidget {
  const MyPopScope({super.key});

  @override
  Widget build(BuildContext context) {
    // return const MaterialApp(
    //   title: 'Flutter WillPopScope demo',
    //   home: WillPopScopeRoute(title: 'Flutter WillPopScope Example'),
    // );
    return const WillPopScopeRoute(title: 'Flutter WillPopScope demo');
  }
}

class WillPopScopeRoute extends StatelessWidget {
  const WillPopScopeRoute({super.key, required this.title});

  final String title;

  Future<bool> _showBackDialog(BuildContext context) async {
    final shouldPop = await showDialog<bool>(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Do you want to go back?'),
          actionsAlignment: MainAxisAlignment.spaceBetween,
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context, true),
              child: const Text('Yes'),
            ),
            TextButton(
              onPressed: () => Navigator.pop(context, false),
              child: const Text('No'),
            ),
          ],
        );
      },
    );
    return shouldPop!;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: getBody(context),
    );
  }

  Widget getBody(context) {
    return PopScope(
      canPop: false,
      onPopInvoked: (bool didPop) async {
        if (didPop) {
          return;
        }
        final bool shouldPop = await _showBackDialog(context);
        if (context.mounted && shouldPop) {
          Navigator.pop(context);
        }
      },
      child: const Text("PopScope"),
    );
  }
}
