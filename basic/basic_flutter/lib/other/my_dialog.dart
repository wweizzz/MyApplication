import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../common/log.dart';

class MyDialog extends StatelessWidget {
  const MyDialog({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Dialog demo',
      home: DialogRoute(title: 'Flutter Dialog demo'),
    );
  }
}

class DialogRoute extends StatelessWidget {
  const DialogRoute({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: getBody(),
    );
  }

  Widget getBody() {
    return ListView.builder(
        itemCount: 6,
        itemExtent: 50.0,
        //列表项构造器
        itemBuilder: (BuildContext context, int index) {
          return ListTile(
            title: Text("$index"),
            onTap: () => _showDialog(context, index),
          );
        });
  }

  _showDialog(BuildContext context, int index) {
    if (index == 0) {
      showSimpleDialog(context);
    } else if (index == 1) {
      showAlertDialog(context);
    } else if (index == 2) {
      showListDialog(context);
    } else if (index == 3) {
      showBottomSheetDialog(context);
    } else if (index == 4) {
      showDatePickerDialog1(context);
    } else if (index == 5) {
      showDatePickerDialog2(context);
    }
  }

  /// showDialog，SimpleDialog
  Future<void> showSimpleDialog(BuildContext context) async {
    int? i = await showDialog<int>(
        context: context,
        builder: (BuildContext context) {
          return SimpleDialog(
            title: const Text('请选择语言'),
            children: <Widget>[
              SimpleDialogOption(
                onPressed: () => Navigator.pop(context, 1),
                child: const Text('中文简体'),
              ),
              SimpleDialogOption(
                onPressed: () => Navigator.pop(context, 2),
                child: const Text('美国英语'),
              ),
            ],
          );
        });
    if (i != null) {
      log("选择了：${i == 1 ? "中文简体" : "美国英语"}");
    }
  }

  /// showDialog，AlertDialog
  Future<void> showAlertDialog(BuildContext context) async {
    bool? delete = await showDialog<bool>(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text("提示"),
          content: const Text("您确定要删除当前文件吗?"),
          actions: <Widget>[
            TextButton(
              child: const Text("取消"),
              onPressed: () => Navigator.of(context).pop(false),
            ),
            TextButton(
              child: const Text("删除"),
              onPressed: () => Navigator.of(context).pop(true),
            ),
          ],
        );
      },
    );
    if (delete != null) {
      if (delete) {
        log("确定删除");
      } else {
        log("取消删除");
      }
    }
  }

  /// showDialog，Dialog
  Future<void> showListDialog(BuildContext context) async {
    int? index = await showDialog<int>(
      context: context,
      builder: (BuildContext context) {
        var child = Column(
          children: <Widget>[
            const ListTile(title: Text("显示菜单列表")),
            Expanded(
              child: ListView.builder(
                itemCount: 20,
                itemBuilder: (BuildContext context, int index) {
                  return ListTile(
                    title: Text("$index"),
                    onTap: () => Navigator.of(context).pop(index),
                  );
                },
              ),
            ),
          ],
        );
        //使用AlertDialog会报错
        //return AlertDialog(content: child);
        return Dialog(child: child);
      },
    );
    if (index != null) {
      log("点击了：$index");
    }
  }

  /// showModalBottomSheet，Dialog
  Future<void> showBottomSheetDialog(BuildContext context) async {
    int? index = await showModalBottomSheet<int>(
      context: context,
      builder: (BuildContext context) {
        return ListView.builder(
          itemCount: 20,
          itemBuilder: (BuildContext context, int index) {
            return ListTile(
              title: Text("$index"),
              onTap: () => Navigator.of(context).pop(index),
            );
          },
        );
      },
    );
    if (index != null) {
      log("点击了：$index");
    }
  }

  /// showDatePicker
  Future<void> showDatePickerDialog1(BuildContext context) async {
    var date = DateTime.now();
    showDatePicker(
      context: context,
      initialDate: date,
      firstDate: date,
      lastDate: date.add(
        const Duration(days: 30),
      ),
    );
  }

  /// showCupertinoModalPopup
  Future<void> showDatePickerDialog2(BuildContext context) async {
    var date = DateTime.now();
    showCupertinoModalPopup(
      context: context,
      builder: (ctx) {
        return Container(
          height: 200,
          color: Colors.white,
          child: CupertinoDatePicker(
            mode: CupertinoDatePickerMode.dateAndTime,
            minimumDate: date,
            maximumDate: date.add(
              const Duration(days: 30),
            ),
            maximumYear: date.year + 1,
            onDateTimeChanged: (DateTime value) {
              log(value);
            },
          ),
        );
      },
    );
  }
}
