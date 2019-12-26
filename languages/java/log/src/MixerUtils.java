package util;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 正则混淆工具，支持xml，path， json 格式
 * *
 * 提供LogMixer接口，以及常用的mixer，可以使用builder自定义mixer，可以使用mixerChain,来组合使用多个mixer，每个mixer支持 key1|key2
 * 这种多key的形式
 *
 * 原理，使用正则表达式的替换功能。
 * 缺点，可能不能很灵活的替换，
 * 优点：可以提供通用的日志混淆工具。
 *
 *
 *
 **/
public class MixerUtils {

    private MixerUtils(){}

    /**
     * 生成一个json替换器Builder
     * @param key
     * @return
     */
    public static LogMixer.PatternBuilder newJsonLogMixerBuilder(String key){
        return new LogMixer.PatternBuilder(key);
    }

    /**
     * xml json 替换builder
     * @param key
     * @return
     */
    public static LogMixer.PatternBuilder newXmlLogMixerBuilder(String key){
        return new LogMixer.PatternBuilder(key, LogMixer.LogMixType.XML);
    }

    /**
     * path 替换builder
     * @param key
     * @return
     */
    public static LogMixer.PatternBuilder newPathLogMixerBuilder(String key){
        return new LogMixer.PatternBuilder(key, LogMixer.LogMixType.PATH);
    }

    /**
     * 责任链混合builder
     * @param key
     * @param type
     * @return
     */
    public static LogMixer.PatternBuilder newLogMixerBuilder(String key,  LogMixer.LogMixType type){
        return new LogMixer.PatternBuilder(key, type);
    }

    /**
     * 身份证替换器
     * @param key
     * @param type
     * @return
     */
    public static LogMixer newIdMixer(String key, LogMixer.LogMixType type){
        return new LogMixer.PatternBuilder(key, type).setBeforeKeepLenth(3).setMaskLength(10).setEndKeepLength(4).build();
    }

    /**
     * 姓名mixer
     * @param key
     * @param type
     * @return
     */
    public static LogMixer newNameMixer(String key, LogMixer.LogMixType type){
        return new LogMixer.PatternBuilder(key, type).setBeforeKeepLenth(0).setMaskLength(1).setEndKeepLength(1).build();
    }

    /**
     * 手机号mixer
     * @param key
     * @param type
     * @return
     */
    public static LogMixer newMobileMixer(String key, LogMixer.LogMixType type){
        return new LogMixer.PatternBuilder(key, type).setBeforeKeepLenth(2).setMaskLength(3).setEndKeepLength(6).build();
    }

    /**
     * 银行卡替换器
     * @param key
     * @param type
     * @return
     */
    public static LogMixer newBankcardMixer(String key, LogMixer.LogMixType type){
        return new LogMixer.PatternBuilder(key, type).setBeforeKeepLenth(3).setMaskLength(4).setEndKeepLength(9).build();
    }

    /**
     * 邮箱替换器
     * @param key
     * @param type
     * @return
     */
    public static LogMixer newEmailMixer(String key, LogMixer.LogMixType type){
        return new LogMixer.EmailMixer(type, key);
    }

    public static LogMixer.MixerChain.MixerChainBuilder neMixerChainBuilder(LogMixer.LogMixType type){
        return new LogMixer.MixerChain.MixerChainBuilder(type);
    }

    private static boolean isEmpty(CharSequence seq){
        return seq == null? true : seq.length() == 0;
    }


    @FunctionalInterface
    public interface LogMixer{
        /**
         * 原始String
         * @param srcString
         * @return
         */
        String replace(String srcString);

        enum LogMixType{
            XML, JSON, PATH
        }



       public class MixerChain implements LogMixer{

            final List<LogMixer> mixerList;

            MixerChain(List<LogMixer> mixers){
                this.mixerList = mixers;
            }

            @Override
            public String replace(final String srcString) {
                if(isEmpty(srcString)){
                    return  srcString;
                }

                String result = srcString;
                for(LogMixer mixer: mixerList){
                    result = mixer.replace(result);
                }
                return result;
            }

            public static class MixerChainBuilder{

                private LogMixType type;

                List<LogMixer> mixerList = new ArrayList<>();

                public MixerChainBuilder(LogMixType type){
                    this.type = type;
                }

               public MixerChainBuilder addIdMixer(String idKey){
                    mixerList.add(MixerUtils.newIdMixer(idKey, type));
                    return this;
                }
                public MixerChainBuilder addEmailMixer(String idKey){
                    mixerList.add(MixerUtils.newEmailMixer(idKey, type));
                    return this;
                }
                public MixerChainBuilder addBankcardMixer(String idKey){
                    mixerList.add(MixerUtils.newBankcardMixer(idKey, type));
                    return this;
                }
                public MixerChainBuilder addNameMixer(String idKey){
                    mixerList.add(MixerUtils.newNameMixer(idKey, type));
                    return this;
                }

                public MixerChainBuilder addMixer(LogMixer logMixer){
                    mixerList.add(logMixer);
                    return this;
                }

                public MixerChainBuilder addMixer(String key, int beforeKeepLength, int endKeepLenth, int maskLength){
                    mixerList.add(MixerUtils.newLogMixerBuilder(key, type).setMaskLength(maskLength).setBeforeKeepLenth(beforeKeepLength)
                            .setEndKeepLength(endKeepLenth).build());
                    return this;
                }

                public MixerChain build(){
                    return new MixerChain(mixerList);
                }

            }
        }

        class EmailMixer implements LogMixer{
            final Pattern pattern ;
            final String replaceStr;
            final String patternStr;


            public EmailMixer(LogMixType type, String key){
                String tempKey = "(?:" + key +")";
                switch (type) {
                    case XML:
                        patternStr = "(<" + tempKey + "[^/]*>\\s*)(.*)([^@]{2}@[^<]*\\s*</" + tempKey + ">)";
                        break;
                    case JSON:
                        patternStr = "((?:\\\\)*\"" + tempKey + "(?:\\\\)*\"\\s*:\\s*(?:\\\\)*\")(.*)([^@]{2}@[^\",]*(?:\\\\)*\")";
                        break;
                    case PATH:
                        patternStr = "([&?]" + tempKey + "=)(.*)([^@]{2}@[^&]*)";
                        break;

                    default:
                        throw new IllegalArgumentException("不支持的混淆类型");
                }

                replaceStr = "$1****$3";
                pattern = Pattern.compile(patternStr);
            }


            @Override
            public String replace(final String srcString) {
                return pattern.matcher(srcString).replaceAll(replaceStr);
            }
        }


        class DefaultLogMixer implements LogMixer{

            final Pattern pattern ;
            final String replaceStr;
            final String patternStr;

            DefaultLogMixer(String  patternStr, String replaceStr){
                this.patternStr = patternStr;
                this.pattern = Pattern.compile(patternStr);
                this.replaceStr = replaceStr;
            }


            @Override
            public String replace(final String srcString) {
                if(isEmpty(srcString)){
                    return  srcString;
                }
                return pattern.matcher(srcString).replaceAll(replaceStr);
            }



        }

        class PatternBuilder{

            String key;
            int beforeKeepLength = 1;
            int maskLength = 6;
            int endKeepLength = 1;
            LogMixType type = LogMixType.JSON;
            private static final String MaskStr = "**********************************************************************";
            private PatternBuilder(String key){
                this.key = key;
            }

            PatternBuilder(String key, LogMixType type){
                this.key = key;
                this.type = type;
            }

            public PatternBuilder setBeforeKeepLenth(int beforeKeepLenth){
                this.beforeKeepLength = beforeKeepLenth;
                return this;
            }

            public  PatternBuilder setMaskLength(int maskLength){
                this.maskLength = maskLength;
                return this;
            }
            public PatternBuilder setEndKeepLength(int endKeepLength){
                this.endKeepLength = endKeepLength;
                return this;
            }



            public LogMixer build(){
                if(maskLength  <= 0){
                    maskLength = 6;
                }

                String  replaceStr = "$1" + getMaskStr(maskLength) + "$3";
                String patternStr = null;
                String tempKey = "(?:" + key +")";
                switch (type){
                    case XML:
                        if(beforeKeepLength <=0 && endKeepLength > 0){
                            patternStr = "(<"+ tempKey + "[^/]*>\\s*)([^<]*)([^<]{"+endKeepLength+"}\\s*</"+tempKey+">)";
                        } else if(beforeKeepLength <=0 && endKeepLength <= 0) {
                            patternStr = "(<" + tempKey + "[^/]*>\\s*)([^<]*)(\\s*</"+key+">)";
                        } else if (beforeKeepLength > 0 && endKeepLength <=0) {
                            patternStr = "(<" + tempKey + "[^/]*>\\s*.{"+beforeKeepLength+"})([^<]*)(\\s*</"+tempKey+">)";
                        }else {
                            patternStr = "(<" + tempKey + "[^/]*>\\s*.{"+beforeKeepLength+"})([^<]*)([^<]{"+endKeepLength+"}\\s*</"+tempKey+">)";
                        }
                        break;
                    case JSON:
                        if(beforeKeepLength <=0 && endKeepLength > 0){
                            patternStr = "((?:\\\\)*\"" + tempKey + "(?:\\\\)*\"\\s*:\\s*(?:\\\\)*\")([^\",]*)" +
                                    "([^\\\\\",]{" + endKeepLength + "}(?:\\\\)*\")";
                        } else if(beforeKeepLength <=0 && endKeepLength <= 0) {
                            patternStr =  "((?:\\\\)*\"" + tempKey + "(?:\\\\)*\"\\s*:\\s*(?:\\\\)*\")([^\",]*)" +
                                    "((?:\\\\)*\")";
                        } else if (beforeKeepLength > 0 && endKeepLength <=0) {
                            patternStr = "((?:\\\\)*\"" + tempKey + "(?:\\\\)*\"\\s*:\\s*(?:\\\\)*\".{" + beforeKeepLength + "})([^\",]*)" +
                                    "((?:\\\\)*\")";
                        }else {
                            patternStr = "((?:\\\\)*\"" + tempKey + "(?:\\\\)*\"\\s*:\\s*(?:\\\\)*\".{" + beforeKeepLength + "})([^\",]*)" +
                                    "([^\\\\\",]{" + endKeepLength + "}(?:\\\\)*\")";
                        }
                        break;
                    case PATH:
                        if(beforeKeepLength <=0 && endKeepLength > 0){
                            patternStr = "([&?]" + tempKey + "=)([^&]*)([^&]{" + endKeepLength + "})";

                        } else if(beforeKeepLength <=0 && endKeepLength <= 0) {
                            patternStr = "([&?]" + tempKey + "=)([^&]*)";
                            replaceStr =  "$1" + getMaskStr(maskLength);
                        } else if (beforeKeepLength > 0 && endKeepLength <=0) {
                            patternStr = "([&?]" + tempKey + "=.{" + beforeKeepLength + "})([^&]*)";
                            replaceStr =  "$1" + getMaskStr(maskLength);
                        }else {
                            patternStr = "([&?]" + tempKey + "=.{" + beforeKeepLength + "})([^&]*)([^&]{" + endKeepLength + "})";
                        }
                        break;
                }

                return new DefaultLogMixer(patternStr, replaceStr);

            }

            public String getMaskStr(int maskLength){
                if(maskLength <= 0){
                    return "";
                }
                return MaskStr.substring(0, maskLength);
            }
        }
    }


    public static void main(String args[]){
        String json = "{\"no\":\"1231231313\",\"email\":\"liujl@qq.com\", \"bizContent\":\"{\\\"no\\\":\\\"12312313\\\"}\"";

        String key = "no";

        System.out.println(MixerUtils.newJsonLogMixerBuilder(key).setBeforeKeepLenth(1).setEndKeepLength(0).setMaskLength(6).build().replace(json));


        key = "NO";

        String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<AIPG><INFO><NO>12312313123131</NO><VERSION>liujl@qq.com</VERSION><DATA_TYPE>0"
            + "</DATA_TYPE>returnUrl</RETURN_URL></BODY></AIPG>";
        System.out.println(MixerUtils.newXmlLogMixerBuilder(key).setBeforeKeepLenth(1).setEndKeepLength(0).setMaskLength(6).build().replace(XML));

        key = "No|a";
        String path = "http://what?a=b&No=abcdefg&email=liuji@qq.com";
        System.out.println(MixerUtils.newEmailMixer("email", LogMixer.LogMixType.JSON).replace(json));
        System.out.println(MixerUtils.newEmailMixer("VERSION", LogMixer.LogMixType.XML).replace(XML));


        System.out.println(MixerUtils.newEmailMixer("email", LogMixer.LogMixType.PATH).replace(path));
        System.out.println(MixerUtils.newPathLogMixerBuilder(key).setBeforeKeepLenth(0).setEndKeepLength(1).setMaskLength(6).build().replace(path));


    }


}
