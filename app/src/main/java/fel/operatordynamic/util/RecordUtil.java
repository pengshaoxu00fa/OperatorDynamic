package fel.operatordynamic.util;




/**
 * Created by XSP on 2016/8/25.
 */
public class RecordUtil {


//    public static ImagePostBean buildPostTextImage(String path,DragDynamicView parent, DragCharView view) {
//        int height = parent.getHeight();
//        Bitmap map = view.buildBitMap();//这个Bitmap不能回收
//        if (map == null) {
//            return null;
//        }
//        ImagePostBean bean = new ImagePostBean();
//        float[] pos = view.getPos();
//        int startY = (int)(pos[1] * (RecordConstant.VIDEO_HEIGHT * 1.00 / height));
//        bean.x = 0;
//        bean.y = startY;
//        bean.startTime = 0;
//        bean.endTime = -1;
//        path += "text_" + startY + "_" + System.currentTimeMillis() + "_result.png";
//        bean.path = BitmapUtils.saveBitmapToPNG(map, path);
//        if (bean.path == null) {
//            return null;
//        }
//        return bean;
//    };
//
//
//    public static ImagePostBean buildPostImage(String path,DragDynamicView parent, ICenterView view, Resources resources , int startTime, int endTime) {
//        if (parent == null || view == null || TextUtils.isEmpty(path)) {
//            return null;
//        }
//        StoryChartlet chartlet = view.getChartlet();
//        if (chartlet == null || TextUtils.isEmpty(chartlet.getOriImage())) {
//            return null;
//        }
//        Bitmap map = null;
//        try {
//            map = decodeOriChartletBitmap(chartlet.getOriImage(),resources);
//        } catch (Throwable e) {
//        }
//        if (map == null) {
//            KwDebug.classicAssert(false, "bitmap第一次读取失败");
//            return null;
//        }
//
//        int padding = ((View)view).getPaddingLeft();
//        float size[] = view.getSize();
//        float centerWidth = size[0] - padding * 2;
//        if (centerWidth <= 0) {
//            centerWidth = 1;
//        }
//        if (map.getWidth() <= 0) {
//            if (!map.isRecycled()) {
//                map.recycle();
//            }
//            return  null;
//        }
//        double scale = centerWidth * 1.00 / map.getWidth();
//        if (!isPNG(chartlet.getOriImage()) || scale >= 0.6) {//非png图片，或者放大过大
//            LogMgr.d("xsp", "buildPostImageV3");
//            return buildPostImageV3(path, map, parent, view, resources, startTime, endTime);
//        } else {
//            LogMgr.d("xsp", "buildPostImageV2");
//            return buildPostImageV2(path,map, parent, view, startTime, endTime);
//        }
//    }
//
//    private static boolean isPNG(String url) {
//        if (url != null && url.toLowerCase().endsWith(".png")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    //图片裁剪定位等 这种裁剪方式适合JPG图片，放大倍数太大的情况
//    private static ImagePostBean buildPostImageV3(String path,Bitmap oriBitmap,DragDynamicView parent, ICenterView view, Resources resources , int startTime, int endTime) {
//        if (parent == null || view == null) {
//            return null;
//        }
//        StoryChartlet chartlet = view.getChartlet();
//        if (chartlet == null || TextUtils.isEmpty(chartlet.getOriImage())) {
//            return null;
//        }
//        ImagePostBean bean = new ImagePostBean();
//        int width = parent.getWidth();
//        int height = parent.getHeight();
//        int padding = ((View)view).getPaddingLeft();
//        float centerPos[] = view.getCenterPos();
//        float size[] = view.getSize();
//        float centerWidth = size[0] - padding * 2;
//        float centerHeight = size[1] - padding * 2;
//        if (centerWidth <= 0) {
//            centerWidth = 1;
//        }
//        if (centerHeight <= 0) {
//            centerHeight = 1;
//        }
//
//        double rotation = ((View)view).getRotation();
//
//        int bw = oriBitmap.getWidth();
//        int bh = oriBitmap.getHeight();
//        double scalex = bw * 1.00 / centerWidth;
//        double scaley = bh * 1.00 / centerHeight;
//        if (scalex == 0 || scaley == 0) {
//            return null;
//        }
//        double scaleWidth = width * scalex;
//        double scaleHeight = height * scaley;
//        double scaleCenterPosX = centerPos[0] * scalex;
//        double scaleCenterPosY = centerPos[1] * scaley;
//
//        double realScaleX =   RecordConstant.VIDEO_WIDTH / scaleWidth;
//        double realScaleY =   RecordConstant.VIDEO_HEIGHT / scaleHeight;
//        Bitmap buildMap = Bitmap.createBitmap(RecordConstant.VIDEO_WIDTH, RecordConstant.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
//        buildMap.eraseColor(resources.getColor(R.color.transparent));
//        PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG); //抗锯齿画图片开启
//        float px = (float)(scaleCenterPosX * realScaleX);
//        float py = (float)(scaleCenterPosY * realScaleY);
//        Canvas canvas = new Canvas(buildMap);
//        Matrix ma = new Matrix();
//        ma.postTranslate(px - bw / 2, py - bh / 2);
//        ma.postScale((float) realScaleX, (float) realScaleY, px, py);
//        ma.postRotate((float) rotation, px, py);
//        canvas.setMatrix(ma);
//        canvas.setDrawFilter(paintFlagsDrawFilter);
//        canvas.drawBitmap(oriBitmap, 0, 0, null);
//
//        bean.x = 0;
//        bean.y = 0;
//        bean.startTime = startTime;
//        bean.endTime = endTime;
//        path += bean.x + "." + bean.y + "_" + startTime + "." + endTime + "_" + System.currentTimeMillis() + "_v3_result.png";
//        bean.path = BitmapUtils.saveBitmapToPNG(buildMap, path);
//        if (TextUtils.isEmpty(bean.path)) {
//            KwDebug.classicAssert(false, "合成图片保存失败");
//            return null;
//        }
//        if (oriBitmap != null && !oriBitmap.isRecycled()) {
//            oriBitmap.recycle();
//            oriBitmap = null;
//        }
//        if (buildMap != null && !buildMap.isRecycled()) {
//            buildMap.recycle();
//            buildMap = null;
//        }
//        return  bean;
//    }
//
//    //图片裁剪定位等 这种裁剪方式适合PNG图片本身比较大，图片放大倍数不大的情况
//    private static ImagePostBean buildPostImageV2(String path,Bitmap oriBitmap, DragDynamicView parent, ICenterView view, int startTime, int endTime) {
//        if (parent == null || view == null) {
//            return null;
//        }
//        StoryChartlet chartlet = view.getChartlet();
//        if (chartlet == null || TextUtils.isEmpty(chartlet.getOriImage())) {
//            return null;
//        }
//        ImagePostBean bean = new ImagePostBean();
//        int width = parent.getWidth();
//        int height = parent.getHeight();
//        int padding = ((View)view).getPaddingLeft();
//        float centerPos[] = view.getCenterPos();
//        float size[] = view.getSize();
//        float centerWidth = size[0] - padding * 2;
//        float centerHeight = size[1] - padding * 2;
//        if (centerWidth < 0) {
//            centerWidth = 0;
//        }
//        if (centerHeight < 0) {
//            centerHeight = 0;
//        }
//        double defRotation = view.getDefRotation();
//        double rotation = ((View)view).getRotation();
//
//        //bitmap 旋转
//        int bmpWidth = oriBitmap.getWidth();
//        int bmpHeight = oriBitmap.getHeight();
//        Matrix matrix = new Matrix();
//        matrix.setRotate((float) rotation, bmpWidth / 2, bmpHeight / 2);
//        Bitmap newBmp = null;
//        try {
//            newBmp = Bitmap.createBitmap(oriBitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
//        } catch (Throwable e) {
//        }
//        if (newBmp == null) {
//            KwDebug.classicAssert(false, "bitmap旋转转换失败");
//            return null;
//        }
//        if (oriBitmap != null && !oriBitmap.isRecycled() && oriBitmap != newBmp) {
//            oriBitmap.recycle();
//            oriBitmap = null;
//        }
//
//        double[][] points = new double[4][2];
//        points[0] = new double[2];
//        points[1] = new double[2];
//        points[2] = new double[2];
//        points[3] = new double[2];
//        double l = Math.sqrt(centerWidth * centerWidth + centerHeight * centerHeight) / 2;
//        double[] point = getPosByDegress(rotation, defRotation, centerPos, l);
//
//        points[0][0] = point[0];
//        points[0][1] = point[1];
//
//        double rotationD = ((180 - 2 * defRotation) + rotation) % 360;
//        double[] pointD = getPosByDegress(rotationD, defRotation, centerPos, l);
//        points[1][0] = pointD[0];
//        points[1][1] = pointD[1];
//
//        //计算旋转后四个点的位置
//        points[2][0] = centerPos[0] - (points[0][0] - centerPos[0]);
//        points[2][1] = centerPos[1] - (points[0][1] - centerPos[1]);
//        points[3][0] = centerPos[0] - (points[1][0] - centerPos[0]);
//        points[3][1] = centerPos[1] - (points[1][1] - centerPos[1]);
//
//        double minX = getMinValue(points[0][0], points[1][0], points[2][0], points[3][0]);
//        double minY = getMinValue(points[0][1], points[1][1], points[2][1], points[3][1]);
//        double maxX = getMaxValue(points[0][0], points[1][0], points[2][0], points[3][0]);
//        double maxY = getMaxValue(points[0][1], points[1][1], points[2][1], points[3][1]);
//        double viewWidth = maxX - minX;
//        double viewHeight = maxY - minY;
//        //没有缩放之前
//        double startCutX = minX >= 0 ? 0 : 0 - minX;//截取X开始位置
//        double startCutY = minY >= 0 ? 0 : 0 - minY;//截取Y结束位置
//
//        double buildStartX = minX < 0? 0: minX;
//        double buildStartY = minY < 0? 0: minY;
//        double buildEndX = maxX > width? width: maxX;
//        double buildEndY = maxY > height? height: maxY;
//
//        double viewToVideoScaleX = RecordConstant.VIDEO_WIDTH * 1.00 / width;
//        double viewToVideoscaleY = RecordConstant.VIDEO_HEIGHT *1.00 / height;
//        buildStartX = buildStartX * viewToVideoScaleX;
//        buildStartY = buildStartY * viewToVideoscaleY;
//        buildEndX = buildEndX * viewToVideoScaleX;
//        buildEndY = buildEndY * viewToVideoscaleY;
//        bmpWidth = newBmp.getWidth();
//        bmpHeight = newBmp.getHeight();
//
//        double bitMap2ViewscaleX = bmpWidth * 1.00 / viewWidth;
//        double bitMap2ViewscaleY = bmpHeight * 1.00 / viewHeight;
//        int realStartCutX = (int)(bitMap2ViewscaleX * startCutX);
//        int realStartCutY = (int)(bitMap2ViewscaleY * startCutY);
//        if (realStartCutX >= bmpWidth) {
//            realStartCutX = bmpWidth - 1;
//        }
//        if (realStartCutY >= bmpHeight) {
//            realStartCutY = bmpHeight - 1;
//        }
//        int realCutWidth;
//        int realCutHeight;
//        if (maxX > width) {
//            realCutWidth = (int)(bitMap2ViewscaleX * width + 0.5);
//        } else {
//            realCutWidth = bmpWidth;
//        }
//        if (maxY > height) {
//            realCutHeight = (int)(bitMap2ViewscaleY * height + 0.5);
//        } else {
//            realCutHeight = bmpHeight;
//        }
//        if (realCutWidth + realStartCutX > bmpWidth) {
//            realCutWidth = bmpWidth - realStartCutX;
//        }
//
//        if (realCutHeight + realStartCutY > bmpHeight) {
//            realCutHeight = bmpHeight - realStartCutY;
//        }
//
//        if (realCutWidth <= 0) {
//            realCutWidth = 1;
//        }
//        if (realCutHeight <= 0) {
//            realCutHeight = 1;
//        }
//        Bitmap cutBmp = null;
//        try {
//            cutBmp = Bitmap.createBitmap(newBmp, realStartCutX, realStartCutY, realCutWidth, realCutHeight, null, true);
//        } catch (Throwable e) {
//        }
//        if (cutBmp == null) {
//            KwDebug.classicAssert(false, "bitmap裁剪转换失败");
//            return null;
//        }
//        if (newBmp != null && !newBmp.isRecycled() && newBmp != cutBmp) {
//            newBmp.recycle();
//            newBmp = null;
//        }
//
//        //要再次缩放
//        double toWidth = buildEndX - buildStartX;
//        double toHeight = buildEndY - buildStartY;
//        double toScalex = toWidth / cutBmp.getWidth();
//        double toScaley = toHeight / cutBmp.getHeight();
//        double toScale = toScalex < toScaley ? toScaley : toScalex;
//        matrix.setScale((float)toScale,  (float)toScale);
//        Bitmap realBmp = null;
//        try {
//            realBmp = Bitmap.createBitmap(cutBmp, 0, 0, cutBmp.getWidth(), cutBmp.getHeight(), matrix, true);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        if (realBmp == null) {
//            KwDebug.classicAssert(false, "bitmap缩放转换失败");
//            return null;
//        }
//        if (cutBmp != null && !cutBmp.isRecycled() && cutBmp != realBmp) {
//            cutBmp.recycle();
//            cutBmp = null;
//        }
//
//        bean.x = (int)buildStartX;
//        bean.y = (int)buildStartY;
//        bean.startTime = startTime;
//        bean.endTime = endTime;
//        path += bean.x + "." + bean.y + "_" + startTime + "." + endTime + "_" + System.currentTimeMillis() + "_v2_result.png";
//        bean.path = BitmapUtils.saveBitmapToPNG(realBmp, path);
//        if (TextUtils.isEmpty(bean.path)) {
//            KwDebug.classicAssert(false, "合成图片保存失败");
//            return null;
//        }
//        if (realBmp != null && !realBmp.isRecycled()) {
//            realBmp.recycle();
//            realBmp = null;
//        }
//        return bean;
//    }





    private static double getMinValue(double p1, double p2, double p3, double p4) {
        double value  = p1;
        if (value > p2) {
            value = p2;
        }
        if (value > p3) {
            value = p3;
        }
        if (value > p4) {
            value = p4;
        }
        return value;
    }

    private static double getMaxValue(double p1, double p2, double p3, double p4) {
        double value  = p1;
        if (value < p2) {
            value = p2;
        }
        if (value < p3) {
            value = p3;
        }
        if (value < p4) {
            value = p4;
        }
        return value;
    }



    private static double[] getPosByDegress(double rotation,double defRotation, float[] centerPos, double len) {
        double point[] = new double[2];
        double degrees = 0;
        if (rotation <= 90 - defRotation  || rotation > 360 - defRotation) {//第一象限
            if (rotation <= 90 - defRotation) {
                degrees = defRotation + rotation;
            } else {
                degrees = defRotation - (360 - rotation);
            }
            double angle = getAngleByDegrees(degrees);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            point[0] = centerPos[0] + len*cos;
            point[1] = centerPos[1] + len * sin;

        }
        if (rotation > 90 - defRotation && rotation <= 180 - defRotation) {//第二象限
            degrees = 180 - (defRotation + rotation);
            double angle = getAngleByDegrees(degrees);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            point[0] = centerPos[0] - len * cos;
            point[1] = centerPos[1] + len * sin;
        }

        if (rotation > 180 - defRotation && rotation <= 270 - defRotation) {//第三象限
            degrees = defRotation + rotation - 180;
            double angle = getAngleByDegrees(degrees);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            point[0] = centerPos[0] - len * cos;
            point[1] = centerPos[1] - len * sin;
        }


        if (rotation > 270 - defRotation && rotation <= 360 - defRotation) {//第四象限
            degrees = 360 - (defRotation + rotation );
            double angle = getAngleByDegrees(degrees);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            point[0] = centerPos[0] + len * cos;
            point[1] = centerPos[1] - len * sin;
        }
        return point;
    }


    public static double getAngleByDegrees(double degress) {
        return 2*Math.PI / 360 * degress;
    }






}
