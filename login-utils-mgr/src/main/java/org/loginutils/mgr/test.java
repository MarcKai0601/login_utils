//package org.loginutils.mgr;
//
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class test {
//
//    private static List<Date[]> getDailyDateRanges(Date start, Date end) {
//        if (start == null) {
//            throw new IllegalArgumentException("start 不能为 null");
//        }
//        if (end == null) {
//            end = new Date(); // 使用当前时间
//        }
//
//        List<Date[]> ranges = new ArrayList<>();
//        ZoneId zone = ZoneId.systemDefault();
//
//        ZonedDateTime zStart = start.toInstant().atZone(zone);
//        ZonedDateTime zEnd = end.toInstant().atZone(zone);
//
//        ZonedDateTime currentStart = zStart.truncatedTo(ChronoUnit.DAYS); // 确保从当天 00:00:00 开始
//        while (!currentStart.isAfter(zEnd)) {
//            // 结束时间为当天的 23:59:59.999
//            ZonedDateTime currentEnd = currentStart.withHour(23).withMinute(59).withSecond(59).withNano(999_000_000);
//            // 如果结束时间超过 zEnd，则使用 zEnd
//            if (currentEnd.isAfter(zEnd)) {
//                currentEnd = zEnd;
//            }
//
//            ranges.add(new Date[]{
//                    Date.from(currentStart.toInstant()),
//                    Date.from(currentEnd.toInstant())
//            });
//
//            // 移动到下一天的 00:00:00
//            currentStart = currentStart.plusDays(1).truncatedTo(ChronoUnit.DAYS);
//        }
//
//        return ranges;
//    }
//
//    // 修改後的 getDailyDateRanges
//    private List<Date[]> getDailyDateRangesNew(Date start, Date end, String timeZoneStr) {
//        // 1. 確定用戶時區，若無則預設
//        ZoneId userZone = ZoneId.of(StringUtils.isBlank(timeZoneStr) ? "GMT+08:00" : timeZoneStr);
//
//        // 2. 將 Timestamp (Date) 轉為該時區的觀點
//        ZonedDateTime zStart = start.toInstant().atZone(userZone);
//        ZonedDateTime zEnd = end.toInstant().atZone(userZone);
//
//        List<Date[]> ranges = new ArrayList<>();
//
//        // 3. 關鍵：以「用戶時區」的 00:00 為循環起點
//        ZonedDateTime currentStart = zStart.truncatedTo(ChronoUnit.DAYS);
//
//        while (currentStart.isBefore(zEnd)) {
//            ZonedDateTime currentEnd = currentStart.with(LocalTime.MAX); // 該時區的 23:59:59.999
//
//            if (currentEnd.isAfter(zEnd)) {
//                currentEnd = zEnd;
//            }
//
//            // 4. 轉回 Date (這會變回不帶時區的絕對 Timestamp) 給資料庫查
//            ranges.add(new Date[]{
//                    Date.from(currentStart.toInstant()),
//                    Date.from(currentEnd.toInstant())
//            });
//
//            currentStart = currentStart.plusDays(1).truncatedTo(ChronoUnit.DAYS);
//        }
//        return ranges;
//    }
//
//    public static void main(String[] args) {
//        // 1770566400122L 換算 UTC 為：2026-02-09 00:00:00.122
//        // 1770652799122L 換算 UTC 為：2026-02-10 00:00:00.122 (跨了剛好一整天多一點)
//        Date start = new Date(1770566400122L);
//        Date end = new Date(1770652799122L);
//
//        test t = new test();
//
//        System.out.println("=== 情況 1: 舊方法 (依賴系統預設時區，假設當前為 GMT+8) ===");
//        List<Date[]> oldResults = getDailyDateRanges(start, end);
//        printRanges(oldResults);
//
//        System.out.println("\n=== 情況 2: 新方法 (指定用戶為 GMT+8) ===");
//        List<Date[]> newResults8 = t.getDailyDateRangesNew(start, end, "GMT+08:00");
//        printRanges(newResults8);
//
//        System.out.println("\n=== 情況 3: 新方法 (指定用戶為 GMT+7) ===");
//        // 這就是你遇到問題的情況：用戶在 +7，看到的時間邊界應該與 +8 不同
//        List<Date[]> newResults7 = t.getDailyDateRangesNew(start, end, "GMT+07:00");
//        printRanges(newResults7);
//    }
//
//    // 輔助列印方法，將 Date 轉為可讀字串
//    private static void printRanges(List<Date[]> ranges) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        // 這裡我們強制用 UTC 列印，才能看清楚「絕對時間」到底有沒有跑掉
//        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
//
//        for (int i = 0; i < ranges.size(); i++) {
//            Date[] r = ranges.get(i);
//            System.out.printf("Day %d -> [Start UTC: %s] ~ [End UTC: %s]%n",
//                    i + 1, sdf.format(r[0]), sdf.format(r[1]));
//        }
//    }
//}
