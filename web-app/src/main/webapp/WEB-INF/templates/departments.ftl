<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <#include "headers.ftl" parse=true>
        </head>

<body>

<#include "head.ftl" parse=true>

    <!-- start content-outer ........................................................................................................................START -->
    <div id="content-outer">
        <!-- start content -->
        <div id="content">

            <!--  start page-heading -->
            <div id="page-heading">
                <h1>${pageHeading}</h1>
            </div>
            <!-- end page-heading -->

            <table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
                <tr>
                    <th rowspan="3" class="sized">
                        <img src="resources/images/shared/side_shadowleft.jpg" width="20" height="300" alt="" />
                    </th>
                    <th class="topleft"></th>
                    <td id="tbl-border-top">&nbsp;</td>
                    <th class="topright"></th>
                    <th rowspan="3" class="sized">
                        <img src="resources/images/shared/side_shadowright.jpg" width="20" height="300" alt="" />
                    </th>
                </tr>
                <tr>
                    <td id="tbl-border-left"></td>
                    <td>
                        <!--  start content-table-inner ...................................................................... START -->
                        <div id="content-table-inner">

                            <h2>Add new department</h2>
                            <form action="" method="post" class="addform" >
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td><input name="name" type="text" class="inp-form" /></td>
                                        <td style="width:5px"></td>
                                        <td>
                                            <input type="submit" value="" class="form-submit" />
                                            <input type="reset" value="" class="form-reset"  />
                                        </td>
                                    </tr>
                                </table>
                            </form>
                            <div class="clear"></div>
                            <br><hr><br>

                            <!--  start table-content  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-->

                            <div id="table-content">

                                <form id="mainform" action="">
                                    <table border="0" width="100%" cellpadding="0" cellspacing="0" id="product-table">
                                        <tr>
                                            <th class="table-header-check"></th>
                                            <th class="table-header-repeat line-left minwidth-1"><a href="">Department name</a>	</th>
                                            <th class="table-header-repeat line-left"><a href="">Average salary</a>	</th>
                                            <th class="table-header-repeat line-left"><a href="">Workers</a>	</th>
                                            <th class="table-header-options line-left"><a href="">Options</a></th>
                                        </tr>
                                        <#assign i = 1>
                                        <#list departments as department>
                                                <#if i%2 == 0>
                                                    <tr>
                                                        <#else>
                                                    <tr class="alternate-row">
                                                </#if>
                                                <td><input  type="hidden" name="${department.id}"/></td>
                                                <td class="datatd">${department.name}</td>
                                                <td>${department.salary}</td>
                                                <td><a href="workers?did=${department.id}">Workers</a> </td>
                                                <td class="options-width">
                                                    <a href="" title="Delete" class="icon-2 info-tooltip"></a>
                                                </td>
                                                </tr>
                                        <#assign i = i+1>
                                        </#list>
                                    </table>
                                </form>

                            </div>
                            <div class="clear"></div>
                        </div>
                        <!--  end content-table-inner ............................................END  -->
                    </td>
                    <td id="tbl-border-right"></td>
                </tr>
                <tr>
                    <th class="sized bottomleft"></th>
                    <td id="tbl-border-bottom">&nbsp;</td>
                    <th class="sized bottomright"></th>
                </tr>
            </table>
            <div class="clear">&nbsp;</div>

        </div>
        <!--  end content -->
        <div class="clear">&nbsp;</div>
    </div>
    <!--  end content-outer........................................................END -->

    <#include "footer.ftl" parse=true>



</body>
</html>
