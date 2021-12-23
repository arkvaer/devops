package me.waver.devops.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import me.waver.devops.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author waver
 * @date 2021/12/23 下午 01:51
 */
@RequestMapping("/gen/nebula")
@RestController
public class NebulaGenerator {
    @GetMapping("/startGen")
    public Result startGen() {
        // C:/Users/arkwa/Sync/Configs/Nebula/publish/src
        // C:/Users/arkwa/Sync/Configs/Nebula/publish/target
        String srcPath = "C:\\Users\\arkwa\\Sync\\Configs\\Nebula\\publish\\src";
        String distPath = "C:\\Users\\arkwa\\Sync\\Configs\\Nebula\\publish\\target";
        List<String> fileNames = FileUtil.listFileNames(srcPath + File.separatorChar + "repo");
        List<String> crtList = fileNames.stream().filter(x -> x.endsWith("crt")).map(x -> x.substring(0, x.length() - 4)).collect(Collectors.toList());
        String srcConfigPath = srcPath + File.separatorChar + "config.yml";
        FileUtil.del(distPath);
        crtList.forEach(name -> {
            String targetPath = distPath + File.separatorChar + name;
            boolean exist = FileUtil.exist(targetPath);
            File targetConfigFile = new File(targetPath);
            FileUtil.copyFile(srcPath + File.separatorChar + "ca.crt", targetPath + File.separatorChar, StandardCopyOption.REPLACE_EXISTING);
            FileUtil.copyFile(srcPath + File.separatorChar + "install.sh", targetPath + File.separatorChar, StandardCopyOption.REPLACE_EXISTING);
            FileUtil.copyFile(srcPath + File.separatorChar + "nebula", targetPath + File.separatorChar, StandardCopyOption.REPLACE_EXISTING);
            FileUtil.copyFile(srcPath + File.separatorChar + "nebula-cert", targetPath + File.separatorChar, StandardCopyOption.REPLACE_EXISTING);
            FileUtil.copyFile(srcPath + File.separatorChar + "nebula.service", targetPath + File.separatorChar, StandardCopyOption.REPLACE_EXISTING);
            if (!exist) {
                FileUtil.mkdir(targetPath);
                targetConfigFile = FileUtil.copyFile(srcConfigPath, targetPath + File.separatorChar + name + ".yml", StandardCopyOption.REPLACE_EXISTING);
            }
            FileReader reader = FileReader.create(targetConfigFile);
            List<String> readLines = reader.readLines();
            FileWriter fileWriter = new FileWriter(targetConfigFile);
            for (int i = 0; i < readLines.size(); i++) {
                if (i >= 2 && i <= 3) {
                    readLines.set(i, readLines.get(i).replace("example", name));
                }
                System.out.println(readLines.get(i));
            }
            fileWriter.writeLines(readLines);
        });
        return Result.ok();
    }
}
