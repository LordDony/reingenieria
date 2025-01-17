package com.google.common.base;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class JdkPattern extends CommonPattern implements Serializable {
    private static final long serialVersionUID = 0;
    private final Pattern pattern;

    private static final class JdkMatcher extends CommonMatcher {
        final Matcher matcher;

        JdkMatcher(Matcher matcher2) {
            Preconditions.checkNotNull(matcher2);
            this.matcher = matcher2;
        }

        public int end() {
            return this.matcher.end();
        }

        public boolean find() {
            return this.matcher.find();
        }

        public boolean matches() {
            return this.matcher.matches();
        }

        public String replaceAll(String str) {
            return this.matcher.replaceAll(str);
        }

        public int start() {
            return this.matcher.start();
        }

        public boolean find(int i) {
            return this.matcher.find(i);
        }
    }

    JdkPattern(Pattern pattern2) {
        Preconditions.checkNotNull(pattern2);
        this.pattern = pattern2;
    }

    public int flags() {
        return this.pattern.flags();
    }

    public CommonMatcher matcher(CharSequence charSequence) {
        return new JdkMatcher(this.pattern.matcher(charSequence));
    }

    public String pattern() {
        return this.pattern.pattern();
    }

    public String toString() {
        return this.pattern.toString();
    }
}
