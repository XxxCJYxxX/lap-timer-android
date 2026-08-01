# LapTimer Android

赛道计时器的 Android 封装应用 —— 基于 WebView 加载 [GitHub Pages](https://xxxcjyxxx.github.io/lap-timer-new/)，带 Apple 风格开屏动画。

## 技术方案

- **WebView wrapper**: 纯 Android WebView 加载 PWA，无需 TWA/Play Store 验证
- **开屏动画**: 自定义 `SplashActivity`，Spring 弹性曲线 + 脉冲光环 + 缩放退场
- **自动构建**: GitHub Actions 编译 Release APK，artifact 下载

## 项目结构

```
app/src/main/
  java/com/xxxcjyxxx/laptimer/
    SplashActivity.java   # 开屏动画（2.5s 序列）
    MainActivity.java     # WebView 加载 lap-timer-new
  res/
    layout/
      activity_splash.xml # 开屏布局
      activity_main.xml   # WebView 容器
    drawable/
      ic_stopwatch.xml    # 矢量秒表图标
      splash_ring.xml     # 脉冲光环
      ic_launcher_*.xml   # 自适应图标
    values/
      themes.xml          # Light theme + 透明状态栏
```

## 开屏动画序列

| 时间 | 动作 |
|------|------|
| 0ms | 蓝色脉冲光环开始扩散 |
| 200ms | 秒表图标弹性缩放进入（`AnticipateInterpolator 1.2f`） |
| 600ms | 标题 "LapTimer" 上滑淡入 |
| 800ms | 副标题 "赛道计时 · GPS 自动分段" 上滑淡入 |
| 2500ms | 图标放大 40x + 淡出，切换至 WebView |

## 本地构建

```bash
# 需要 Android Studio 或 Android SDK
./gradlew assembleRelease
# APK 输出: app/build/outputs/apk/release/app-release-unsigned.apk
```

## GitHub Actions 自动构建

推送至 `main` 分支自动触发。在 Actions 页面下载 `LapTimer-release` artifact。
