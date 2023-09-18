import 'package:basic_flutter/common/notification.dart';
import 'package:flutter/material.dart';

class MyNotification extends StatelessWidget {
  const MyNotification({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Notification demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Notification demo'),
        ),
        body: const Center(child: NotificationRoute()),
      ),
    );
  }
}

class NotificationRoute extends StatefulWidget {
  const NotificationRoute({super.key});

  @override
  State<NotificationRoute> createState() => _NotificationRouteState();
}

class _NotificationRouteState extends State<NotificationRoute> {
  late NotificationHelper _notificationHelper;

  @override
  void initState() {
    super.initState();
    _notificationHelper = NotificationHelper();
    _notificationHelper.initialize();
  }

  _showNotification() {
    _notificationHelper.showNotification(
      id: 1,
      title: 'Hello',
      body: 'This is a notification!',
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _showNotification(),
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
