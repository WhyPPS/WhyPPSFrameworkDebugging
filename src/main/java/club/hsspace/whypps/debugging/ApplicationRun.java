package club.hsspace.whypps.debugging;

import club.hsspace.whypps.framework.app.annotation.AppInterface;
import club.hsspace.whypps.framework.app.annotation.AppStart;
import club.hsspace.whypps.framework.run.WhyPPSFramework;
import club.hsspace.whypps.manage.ContainerManage;

/**
 * @ClassName: ApplicationRun
 * @CreateTime: 2022/6/6
 * @Comment:
 * @Author: Qing_ning
 * @Mail: 1750359613@qq.com
 */
@AppInterface
public class ApplicationRun {

    @AppStart(thread = true)
    public void main(ContainerManage containerManage) {
        Thread.currentThread().setContextClassLoader(ApplicationRun.class.getClassLoader());
        HelloApplication.run(containerManage);
    }

}
