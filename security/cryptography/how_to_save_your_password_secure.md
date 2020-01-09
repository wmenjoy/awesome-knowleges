# 怎么保存密码

在很多账户中管理不同的密码通常是困难的: 复杂的密码，包括手工制作的和由随机密码生成器生成的密码，很难记住，但是简单的密码通常不安全。 像 LastPass 这样的云密码管理器也不是一个安全的选择: 云密码管理器经常会遇到各种安全问题。 除此之外，把你的密码暴露给云密码管理公司也不是什么好事。 使用校验和，管理工作可以很容易地由我们自己完成，同时仍然保持“良好”的密码。


校验和是小型字符串，可以通过特定的校验和算法从其他字符串中计算出来。 使用最流行的校验和算法，例如 MD5、 SHA-1等，校验和通常看起来与原始字符串非常不同，甚至改变原始字符串的一个比特通常导致非常不同的校验和。 例如，两个相似的单词“ bird”和“ birds”的 MD5校验和是完全不同的(你可以使用本网站计算一个字符串的校验和，或者使用 gnu / linux 或 Max OS x 的 md5sum 或 sha1sum 命令行工具) :

STRING|字符串|MD5 CHECKSUM
--------|-----|---------------
bird|鸟|87d28160e9215b17645c734ba7170ba1
birds|鸟|ea5f5a5293a7d404e091c04939ba2ad8