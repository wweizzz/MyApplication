import 'package:flutter/material.dart';

///CustomPaint，CustomPainter
class MyPaint extends StatelessWidget {
  const MyPaint({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Paint demo',
      home: PaintRoute(title: 'Flutter Paint demo'),
    );
  }
}

class PaintRoute extends StatefulWidget {
  const PaintRoute({super.key, required this.title});

  final String title;

  @override
  State<PaintRoute> createState() => _PaintRouteState();
}

class _PaintRouteState extends State<PaintRoute> {
  List<Offset?> _points = <Offset>[];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body: GestureDetector(
          onPanUpdate: (details) {
            setState(() {
              RenderBox? referenceBox = context.findRenderObject() as RenderBox;
              Offset localPosition =
                  referenceBox.globalToLocal(details.globalPosition);
              _points = List.from(_points)..add(localPosition);
            });
          },
          onPanEnd: (details) => _points.add(null),
          child: CustomPaint(
            painter: SignaturePainter(_points),
            size: Size.infinite,
          ),
        ));
  }
}

class SignaturePainter extends CustomPainter {
  SignaturePainter(this.points);

  final List<Offset?> points;

  @override
  void paint(Canvas canvas, Size size) {
    var paint = Paint()
      ..color = Colors.black
      ..strokeCap = StrokeCap.round
      ..strokeWidth = 5;
    for (int i = 0; i < points.length - 1; i++) {
      if (points[i] != null && points[i + 1] != null) {
        canvas.drawLine(points[i]!, points[i + 1]!, paint);
      }
    }
  }

  @override
  bool shouldRepaint(SignaturePainter oldDelegate) {
    return oldDelegate.points != points;
  }
}
