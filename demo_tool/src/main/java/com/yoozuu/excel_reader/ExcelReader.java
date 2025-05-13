package com.yoozuu.excel_reader;

import com.yoozuu.excel_reader.entity.SkillEffectEntity;
import util.ExcelReaderUtil;
import util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {
    public static void main(String[] args) {
        try {
            File file = new File("demo_tool/src/main/resources/skill_effect.xlsx");
            List<SkillEffectEntity> skillEffectEntities = ExcelReaderUtil.parseExcelToEntity(file, SkillEffectEntity.class, 5);
            Map<Integer, SkillEffectEntity> map = new HashMap<>();
            for (SkillEffectEntity skillEffectEntity : skillEffectEntities) {
                map.put(skillEffectEntity.getId(), skillEffectEntity);
            }
            List<String> allLines = FileUtil.readFileToString("demo_tool/src/main/resources/errorBattleReport.txt", "utf-8");
            allLines.stream().filter(e -> e.contains("skillEffectId")).forEach( e -> {
                String[] split = e.split("=");
                if (split.length > 1) {
                    int skillId = Integer.parseInt(split[split.length - 1]);
                    System.out.println("技能id： " + skillId + " 技能效果： " + map.get(skillId));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
