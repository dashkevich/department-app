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

                            <h2>Add new worker</h2>
                            <!-- start id-form -->
                            <form action="workers/add" method="post" class="addform">
                                <table border="0" cellpadding="0" cellspacing="0"  id="id-form">
                                    <tr>
                                        <th valign="top">Worker name:</th>
                                        <td>
                                            <div class="ws-datepicker">
                                                <input type="text" name="workerName" class="inp-form" />
                                            </div>
                                        </td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <th valign="top">Department:</th>
                                        <td>
                                            <select  class="styledselect_form_1" name="workerDepartment">
                                                <#list departments as department>
                                                    <option value="${department.id}">${department.name}</option>
                                                </#list>
                                            </select>
                                        </td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <th valign="top">Worker salary:</th>
                                        <td><input type="text" class="inp-form" name="workerSalary" /></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <th valign="top">Worker birthday:</th>
                                        <td>
                                            <input type="text" name="workerBirthday" class="inp-form" id="worker_birthday" />
                                        </td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <th>&nbsp;</th>
                                        <td valign="top">
                                            <input type="submit" value="" class="form-submit" />
                                            <input type="reset" value="" class="form-reset"  />
                                        </td>
                                        <td></td>
                                    </tr>
                                </table>
                            </form>
                            <!-- end id-form  -->
                            <div class="clear"></div>
                            <br><hr><br>


                            <!-- workers search by date form -->
                            <form action="" method="" class="searchform">
                                <h3>Search workers by birthday:</h3>
                                <table border="0" cellpadding="0" cellspacing="0" id="workers_search_table">
                                    <tr>
                                        <td>
                                            <input placeholder="DateFrom" type="text" name="dateFrom" class="inp-form" id="dateFrom"/>
                                        </td>
                                        <td>
                                            <input placeholder="DateTo" type="text" name="dateTo" class="inp-form" id="dateTo" />
                                        </td>
                                        <td valign="top">
                                            <input type="submit" value="" class="form-submit" />
                                            <input type="reset" value="" class="form-reset"  />
                                        </td>
                                        <td></td>
                                    </tr>
                                </table>
                            </form>
                            <!-- end workers search by date form -->

                            <!--  start table-content  -->

                            <div id="table-content">
                                <!-- Single Pick -->
                                <form id="mainform" action="">
                                    <table border="0" width="100%" cellpadding="0" cellspacing="0" id="product-table">
                                        <tr>
                                            <th class="table-header-check"><a href="" ><strong></strong></a></th>
                                            <th class="table-header-repeat line-left minwidth-1"><a href="">Name</a></th>
                                            <th class="table-header-repeat line-left"><a href="">Department</a>	</th>
                                            <th class="table-header-repeat line-left"><a href="">Birthday</a>	</th>
                                            <th class="table-header-repeat line-left"><a href="">Salary</a>	</th>
                                            <th class="table-header-options line-left"><a href="">Options</a></th>
                                        </tr>
                                        <#assign i = 1>
                                            <#list workers as worker>
                                                <#if i%2 == 0>
                                                    <tr><#else>
                                                    <tr class="alternate-row">
                                                </#if>

                                                <td><input type="hidden" name="${worker.id}"/></td>

                                                <td class="datatd">${worker.name}</td>
                                                <td class="datatd">
                                                    <#list departments as department>
                                                        <#if department.id == worker.departmentID?string>
                                                            ${department.name}
                                                        </#if>
                                                    </#list>
                                                </td>
                                                <td class="datatd">${worker.birthday}</td>
                                                <td class="datatd">${worker.salary}</td>

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
