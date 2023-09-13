import 'package:flutter/material.dart';

///WillPopScope 导航返回拦截
///每个 WillPopScope 仅对当前组件生效，不可在 MaterialApp 上使用。
class MyWillPopScope extends StatelessWidget {
  const MyWillPopScope({super.key});

  @override
  Widget build(BuildContext context) {
    // return MaterialApp(
    //     title: 'Flutter WillPopScope demo',
    //     home: Scaffold(
    //       appBar: AppBar(
    //         title: const Text('Flutter WillPopScope demo'),
    //       ),
    //       body: const Center(child: WillPopScopeRoute()),
    //     ));
    return Scaffold(
      appBar: AppBar(
        title: const Text('Flutter WillPopScope demo'),
      ),
      body: const Center(child: WillPopScopeRoute()),
    );
  }
}

class WillPopScopeRoute extends StatelessWidget {
  const WillPopScopeRoute({super.key});

  Future<bool> _showAlertDialog(BuildContext context) async {
    final shouldPop = await showDialog<bool>(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Do you want to go back?'),
          actionsAlignment: MainAxisAlignment.spaceBetween,
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context, true);
              },
              child: const Text('Yes'),
            ),
            TextButton(
              onPressed: () {
                Navigator.pop(context, false);
              },
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
    return WillPopScope(
      onWillPop: () => _showAlertDialog(context),
      child: Container(
        alignment: Alignment.center,
        child: const Text("WillPopScope"),
      ),
    );
  }
}
