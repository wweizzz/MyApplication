import 'package:flutter/material.dart';

import 'page/my_provider_page.dart';

/// Provider
/// https://pub.dev/packages/provider
class MyProvider extends StatelessWidget {
  const MyProvider({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Provider demo',
      home: MyProviderPage(title: 'Flutter Provider demo'),
    );
  }
}
