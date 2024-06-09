import 'package:flutter/material.dart';

import '../common/notification.dart';

/// Notifications
/// https://pub.dev/packages/flutter_local_notifications
class MyNotification extends StatelessWidget {
  const MyNotification({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Notification demo',
      home: NotificationRoute(title: 'Flutter Notification demo'),
    );
  }
}

class NotificationRoute extends StatefulWidget {
  const NotificationRoute({super.key, required this.title});

  final String title;

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
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: getBody(),
      floatingActionButton: getFAB(),
    );
  }

  Widget getBody() {
    return const Center();
  }

  Widget getFAB() {
    return FloatingActionButton(
      onPressed: () => _showNotification(),
      tooltip: 'notification',
      child: const Icon(Icons.add),
    );
  }
}
