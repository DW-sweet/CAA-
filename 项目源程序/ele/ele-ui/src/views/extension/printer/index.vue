<template>
  <div class="ele-body ele-body-card">
    <a-card title="打印当前页面" :bordered="false">
      <a-form
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
        style="max-width: 320px"
      >
        <a-form-item label="纸张方向">
          <a-select
            allow-clear
            :value="{ true: 1, false: 0 }[String(option1.horizontal)]"
            placeholder="不设置"
            @update:value="updateHorizontal"
          >
            <a-select-option :value="1">横向</a-select-option>
            <a-select-option :value="0">纵向</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="页面间距">
          <a-select
            allow-clear
            v-model:value="option1.margin"
            placeholder="不设置"
          >
            <a-select-option value="0px">0px</a-select-option>
            <a-select-option value="50px">50px</a-select-option>
            <a-select-option value="100px">100px</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="页面标题">
          <a-input
            allow-clear
            v-model:value="option1.title"
            placeholder="不设置"
          />
        </a-form-item>
      </a-form>
      <a-space>
        <a-button @click="print">打印</a-button>
        <a-button @click="printHide">打印隐藏指定内容</a-button>
      </a-space>
      <div style="margin-top: 16px">
        <span class="ele-text-primary ele-printer-hide">
          此段内容在所有打印时隐藏, 打印完复原。
        </span>
        <span class="ele-text-danger demo-hide-1">
          此段内容在指定打印时才隐藏。
        </span>
      </div>
    </a-card>
    <a-card title="打印任意内容" :bordered="false">
      <a-form
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
        style="max-width: 320px"
      >
        <a-form-item label="loading">
          <a-radio-group v-model:value="option2.loading">
            <a-radio :value="false">不显示</a-radio>
            <a-radio :value="true">显示</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
      <a-space>
        <a-button @click="printAnyHtml">打印任意内容</a-button>
        <a-button @click="printAddHeader">设置页眉页脚</a-button>
        <a-button @click="printImage">打印图片</a-button>
      </a-space>
    </a-card>
    <a-card title="分页打印" :bordered="false">
      <a-space>
        <a-button @click="printAnyPage">分页打印</a-button>
        <a-button @click="printPageAddHeader">分页打印设置页眉页脚</a-button>
      </a-space>
    </a-card>
    <a-card title="进阶示例" :bordered="false">
      <a-space>
        <a-button @click="printDataTable">打印数据表格</a-button>
        <a-tooltip title="对于复杂的打印需求，可以后端生成pdf给前端打印">
          <a-button @click="printPdfUrl">打印pdf</a-button>
        </a-tooltip>
        <a-button @click="printQrCode">打印条码</a-button>
        <a-button @click="printAnyTable">打印自定义表格</a-button>
      </a-space>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import {
    printThis,
    printHtml,
    printPage,
    printPdf,
    makeTable
  } from 'ele-admin-pro';
  import type { PrintHtmlOption } from 'ele-admin-pro';

  interface UserType {
    key: number;
    username: string;
    amount: number;
    province: string;
    city: string;
    zone: string;
    street: string;
    address: string;
  }

  // 打印当前页面参数
  const option1 = reactive<PrintHtmlOption>({
    horizontal: undefined,
    margin: undefined,
    title: ''
  });

  // 打印任意内容参数
  const option2 = reactive({
    loading: false
  });

  const users = ref<UserType[]>([
    {
      key: 1,
      username: '张小三',
      amount: 18,
      province: '浙江',
      city: '杭州',
      zone: '西湖区',
      street: '西溪街道',
      address: '西溪花园30栋1单元'
    },
    {
      key: 2,
      username: '李小四',
      amount: 39,
      province: '江苏',
      city: '苏州',
      zone: '姑苏区',
      street: '丝绸路',
      address: '天墅之城9幢2单元'
    },
    {
      key: 3,
      username: '王小五',
      amount: 8,
      province: '江西',
      city: '南昌',
      zone: '青山湖区',
      street: '艾溪湖办事处',
      address: '中兴和园1幢3单元'
    },
    {
      key: 4,
      username: '赵小六',
      amount: 16,
      province: '福建',
      city: '泉州',
      zone: '丰泽区',
      street: '南洋街道',
      address: '南洋村6幢1单元'
    },
    {
      key: 5,
      username: '孙小七',
      amount: 12,
      province: '重庆',
      city: '重庆',
      zone: '沙坪坝区',
      street: '虎溪街道',
      address: '重庆师范大学'
    },
    {
      key: 6,
      username: '周小八',
      amount: 11,
      province: '安徽',
      city: '黄山',
      zone: '黄山区',
      street: '汤口镇',
      address: '温泉村21号'
    }
  ]);

  /* 打印当前页面 */
  const print = () => {
    printThis(option1);
  };

  /* 打印隐藏指定内容 */
  const printHide = () => {
    printThis({
      ...option1,
      hide: ['.demo-hide-1']
    });
  };

  /* 打印任意内容 */
  const printAnyHtml = () => {
    printHtml({
      ...option2,
      html: [
        '<h1 style="color: #1890ff;">EleAdmin 后台管理模板</h1>',
        '<div style="color: #F51D2C;">通用型后台管理模板，界面美观、开箱即用</div>'
      ].join('')
    });
  };

  /* 打印设置页眉页脚 */
  const printAddHeader = () => {
    printHtml({
      ...option2,
      margin: 0,
      html: [
        '<div style="padding: 0 60px;">',
        Array(38).join('<h3>EleAdmin 后台管理模板</h3>'),
        '</div>'
      ].join(''),
      header: `
        <div style="display: flex;font-size: 12px;padding: 15px 30px 25px;">
          <div>我是页眉左侧</div>
          <div style="flex: 1;text-align: center;">我是页眉</div>
          <div>我是页眉右侧</div>
        </div>`,
      footer: `
        <div style="display: flex;font-size: 12px;padding: 15px 30px 25px;">
          <div>我是页脚左侧</div>
          <div style="flex: 1;text-align: center;">我是页脚</div>
          <div>我是页脚右侧</div>
        </div>`,
      style: '<style> h3 { color: red; } </style>'
    });
  };

  /* 打印图片 */
  const printImage = () => {
    printHtml({
      ...option2,
      margin: 0,
      html: '<img src="https://cdn.eleadmin.com/20200610/LrCTN2j94lo9N7wEql7cBr1Ux4rHMvmZ.jpg" style="width: 100%;"/>'
    });
  };

  /* 分页打印 */
  const printAnyPage = () => {
    printPage({
      ...option2,
      pages: [
        '<h3>我是第一页</h3>',
        '<h3>我是第二页</h3>',
        '<h3>我是第三页</h3>',
        '<h3>我是第四页</h3>',
        '<h3>我是第五页</h3>'
      ],
      style: '<style> h3 { color: red; } </style>'
    });
  };

  /* 分页打印设置页眉页脚 */
  const printPageAddHeader = () => {
    printPage({
      ...option2,
      pages: [
        '<h3>我是第一页</h3>',
        '<h3>我是第二页</h3>',
        '<h3>我是第三页</h3>',
        '<h3>我是第四页</h3>',
        '<h3>我是第五页</h3>'
      ],
      margin: 0,
      padding: '20px 60px',
      header: `
        <div style="display: flex;font-size: 12px;padding: 15px 30px;">
          <div>我是页眉左侧</div>
          <div style="flex: 1;text-align: center;">我是页眉</div>
          <div>我是页眉右侧</div>
        </div>`,
      footer: `
        <div style="display: flex;font-size: 12px;padding: 15px 30px;">
          <div>我是页脚左侧</div>
          <div style="flex: 1;text-align: center;">我是页脚</div>
          <div>我是页脚右侧</div>
        </div>`,
      style: '<style> h3 { color: red; } </style>'
    });
  };

  /* 打印数据表格 */
  const printDataTable = () => {
    const html = makeTable(users.value, [
      [
        {
          field: 'username',
          width: 150,
          rowspan: 2,
          title: '联系人'
        },
        {
          align: 'center',
          colspan: 3,
          title: '地址'
        },
        {
          field: 'amount',
          width: 120,
          rowspan: 2,
          title: '金额',
          align: 'center'
        }
      ],
      [
        {
          field: 'province',
          width: 120,
          title: '省'
        },
        {
          field: 'city',
          width: 120,
          title: '市'
        },
        {
          width: 200,
          title: '区',
          templet: (d) => {
            return `<span style="color: red;">${d.zone}</span>`;
          }
        }
      ]
    ]);
    printHtml({
      ...option2,
      html: '<p>提供数据和cols配置自动生成复杂表格，非常的方便</p>' + html
    });
  };

  /* 打印pdf */
  const printPdfUrl = () => {
    printPdf({
      url: 'https://cdn.eleadmin.com/20200610/20200708224450.pdf'
    });
  };

  /* 打印条码 */
  const printQrCode = () => {
    const html = `
      <div class="code-group">
        <div class="code-group-title">EasyWeb授权凭证</div>
        <div class="code-group-body">
          <p>手机扫描右侧二维码，或登录</p>
          <p>网站https://easyweb.vip</p>
          <p>查询产品真伪</p>
          <img src="https://cdn.eleadmin.com/20200610/20200708230820.png" width="70px" height="70px"/>
          <span>515AE3X1</span>
        </div>
      </div>
      <style>
        .code-group {
          display: inline-block;
          border: 1px solid #ccc;
          border-radius: 5px;
          background-color: #fff;
        }
        .code-group-title {
          border-bottom: 1px solid #ccc;
          padding: 10px 15px;
          text-align: center;
          font-size: 18px;
        }
        .code-group-body {
          text-align: center;
          position: relative;
          padding: 15px 115px 0 25px;
          min-height: 90px;
        }
        .code-group-body > p {
          margin: 0 0 13px 0;
          font-size: 15px;
          font-family: 幼圆;
          color: #333;
          font-weight: 600;
        }
        .code-group-body > img, .code-group-body > span {
          position: absolute;
          right: 25px;
          top: 15px;
        }
        .code-group-body > span {
          top: 90px;
        }
      </style>
    `;
    printHtml({
      ...option2,
      html: html
    });
  };

  /* 打印自定义表格 */
  const printAnyTable = () => {
    const html = `
      <h2 style="text-align: center;color: #333;">XxxXx班课程表</h2>
      <table class="ele-printer-table">
        <colgroup>
          <col width="130px"/>
        </colgroup>
        <tr>
          <th style="position: relative;">
            <div style="position: absolute;right: 20px;top: 10px;line-height: normal;">星期</div>
            <div style="position: absolute;left: 20px;bottom: 10px;line-height: normal;">时间</div>
            <div
              style="border-top: 1px solid #000;width:141px;height: 0;position: absolute;left: 0;top: 0;transform: rotate(22deg);transform-origin: 0 0;">
            </div>
          </th>
          <th>周一</th>
          <th>周二</th>
          <th>周三</th>
          <th>周四</th>
          <th>周五</th>
        </tr>
        <tr>
          <td>8:00-10:00</td>
          <td>HTML5网页设计<br/>曲丽丽 - 441教室</td>
          <td>数据库原理及应用<br/>严良 - 716机房</td>
          <td>JavaSE初级程序设计<br/>肖萧 - 715机房</td>
          <td></td>
          <td>JavaScript程序设计<br/>董娜 - 733机房</td>
        </tr>
        <tr>
          <td>10:30-12:30</td>
          <td></td>
          <td>JavaScript程序设计<br/>董娜 - 733机房</td>
          <td></td>
          <td>锋利的jQuery<br/>程咏 - 303教室</td>
          <td>JavaEE应用开发<br/>周星 - 303教室</td>
        </tr>
        <tr>
          <td colspan="6" style="height: auto;">午休</td>
        </tr>
        <tr>
          <td>13:30-15:30</td>
          <td>JavaSE初级程序设计<br/>肖萧 - 715机房</td>
          <td></td>
          <td>HTML5网页设计<br/>曲丽丽 - 441教室</td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>16:00-18:00</td>
          <td></td>
          <td>JavaEE应用开发<br/>周星 - 303教室</td>
          <td></td>
          <td>数据库原理及应用<br/>严良 - 716机房</td>
          <td></td>
        </tr>
      </table>
      <style>
        th, td {
          text-align: center;
          line-height: 35px;
        }
        td {
          height: 100px;
        }
      </style>
    `;
    printHtml({
      ...option2,
      html: html,
      horizontal: true,
      title: '.'
    });
  };

  const updateHorizontal = (value?: number) => {
    option1.horizontal = { '1': true, '0': false }[String(value)];
  };
</script>

<script lang="ts">
  export default {
    name: 'ExtensionPrinter'
  };
</script>

<style scoped>
  .ant-space {
    flex-wrap: wrap;
  }

  .ant-space .ant-btn {
    margin-bottom: 8px;
  }
</style>
