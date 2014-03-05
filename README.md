xls2kml
=======



[English Version](#english-version)

将MS Excel文件转换为google地球的kml文件，可导入POI点

*需要JRE环境 -- need jre (Java Runtime Environment)*

==__使用步骤__==

1. 点击“打开”选择想要转换的原始excel文档.
2. 确认是否有分类内容列，默认为无分类
3. 点击生成，在原excel文档位置生成kml文件

> __Excel的数据格式__
> 
> 无分类

> 共三列，分别为：地点名称，经度，纬度

> 如：舒兰,126.9409556581703,44.41078056918841

> 有分类

> 共四列，分别为：文件夹名、地点名称，经度，纬度

> 如：吉林,舒兰,126.9409556581703,44.41078056918841

===__历史__===

1. _创建项目 (2012-2-27) - 支持xls和xlsx格式文件_
2. _修改(2012-4-12) - 转换为exe文件，但仍需jre环境_
3. _2012-6-6 - 增加英语支持，其他语言未处理_
4. _2012-6-7 - 改为使用StringBuffer_
5. _2012-6-18 - 增加菜单和语言切换_

---


English Version
=

convert *MS Excel* file(xls or xlsx) to google earth file(.kml)

===__Instructions__===

1. click the 'open' button to choose a excel file
2. if there is catogery coloum in the file, check the *catogery* checkbox
3. click 'generate', the kml file will be saved to the folder which contains the excel file

> __Data Format in Excel__

> without catogery

> 3 columes: Name, Longitude, Latitude

> e.g. shulan, 126.9409556581703, 44.41078056918841

> with catogery

> 4 columes: Catogery, Name, Longitude, Latitude

> e.g. jilin, shulan, 126.9409556581703, 44.41078056918841

===__History__===

1. add project (2012-2-27)  -  _support xls and xlsx file_
2. modification (2012-4-12)  -  _need jre, even the exe file_
3. 2012-6-6  -  _add english support_
4. 2012-6-7  -  _replace String with StringBuffer_
5. 2012-6-18  -  _add menu and change language dynamically_
