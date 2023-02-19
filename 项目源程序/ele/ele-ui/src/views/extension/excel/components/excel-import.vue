<template>
  <a-card title="导入excel" :bordered="false">
    <!-- 操作按钮 -->
    <ele-toolbar :tools="[]">
      <a-space>
        <a-upload
          :before-upload="importFile"
          :show-upload-list="false"
          accept=".xls,.xlsx,.csv"
        >
          <a-button type="primary">导入excel</a-button>
        </a-upload>
        <a-upload
          :before-upload="importFile2"
          :show-upload-list="false"
          accept=".xls,.xlsx,.csv"
        >
          <a-button type="primary">导入拆分合并</a-button>
        </a-upload>
        <a-upload
          :before-upload="importFile3"
          :show-upload-list="false"
          accept=".xls,.xlsx,.csv"
        >
          <a-button type="primary">导入保持合并</a-button>
        </a-upload>
      </a-space>
    </ele-toolbar>
    <div class="ant-table ant-table-middle ant-table-bordered">
      <div class="ant-table-container">
        <div class="ant-table-content" style="overflow-x: auto">
          <table style="min-width: max-content">
            <thead class="ant-table-thead">
              <tr>
                <th></th>
                <th
                  v-for="item in importTitle"
                  :key="item"
                  style="text-align: center"
                >
                  {{ item }}
                </th>
              </tr>
            </thead>
            <tbody class="ant-table-tbody">
              <tr
                v-for="(item, index) in importData"
                :key="index"
                class="ant-table-row"
              >
                <td style="text-align: center">{{ index + 1 }}</td>
                <template v-for="key in importTitle">
                  <td
                    v-if="
                      item['__colspan__' + key] !== 0 &&
                      item['__rowspan__' + key] !== 0
                    "
                    :key="key"
                    :colspan="item['__colspan__' + key]"
                    :rowspan="item['__rowspan__' + key]"
                    style="text-align: center"
                  >
                    {{ item[key] }}
                  </td>
                </template>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <a-row :gutter="32">
      <a-col :md="12" :xs="24">
        <div style="margin: 16px 0">二维数组格式数据:</div>
        <pre
          style="max-height: 300px; padding: 16px; overflow: auto"
          class="ele-bg-base"
          >{{ JSON.stringify(importDataAoa, null, 4) }}
          </pre
        >
      </a-col>
      <a-col :md="12" :xs="24">
        <div style="margin: 16px 0">JSON格式数据:</div>
        <pre
          style="max-height: 300px; padding: 16px; overflow: auto"
          class="ele-bg-base"
          >{{ JSON.stringify(importData, null, 4) }}
          </pre
        >
      </a-col>
    </a-row>
  </a-card>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import XLSX from 'xlsx';

  // 导入数据的列
  const importTitle = ref<string[]>(['A', 'B', 'C', 'D', 'E', 'F', 'G']);

  // 导入的数据
  const importData = ref<Record<string, any>[]>([]);

  // 导入数据二维数组形式
  const importDataAoa = ref<(string | number)[][]>([]);

  /* 导入本地excel文件 */
  const importFile = (file: File) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const data = new Uint8Array(e.target?.result as any);
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetNames = workbook.SheetNames;
      const worksheet = workbook.Sheets[sheetNames[0]];
      // 解析成二维数组
      const aoa = XLSX.utils.sheet_to_json<string[]>(worksheet, { header: 1 });
      // 生成表格需要的数据
      let list: Record<string, any>[] = [];
      let maxCols = 0;
      let title: string[] = [];
      aoa.forEach((d) => {
        if (d.length > maxCols) {
          maxCols = d.length;
        }
        const row = {};
        for (let i = 0; i < d.length; i++) {
          const key = getCharByIndex(i);
          row[key] = d[i];
          row['__colspan__' + key] = 1;
          row['__rowspan__' + key] = 1;
        }
        list.push(row);
      });
      for (let i = 0; i < maxCols; i++) {
        title.push(getCharByIndex(i));
      }
      importTitle.value = title;
      importData.value = list;
      importDataAoa.value = aoa;
    };
    reader.readAsArrayBuffer(file);
    return false;
  };

  /* 导入excel拆分合并单元格 */
  const importFile2 = (file: File) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const data = new Uint8Array(e.target?.result as any);
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetNames = workbook.SheetNames;
      const worksheet = workbook.Sheets[sheetNames[0]];
      // 解析成二维数组
      const aoa = XLSX.utils.sheet_to_json<string[]>(worksheet, { header: 1 });
      // 拆分合并单元格
      if (worksheet['!merges']) {
        worksheet['!merges'].forEach((m) => {
          for (let r = m.s.r; r <= m.e.r; r++) {
            for (let c = m.s.c; c <= m.e.c; c++) {
              aoa[r][c] = aoa[m.s.r][m.s.c];
            }
          }
        });
      }
      // 生成表格需要的数据
      let list: Record<string, any>[] = [];
      let maxCols = 0;
      let title: string[] = [];
      aoa.forEach((d) => {
        if (d.length > maxCols) {
          maxCols = d.length;
        }
        const row = {};
        for (let i = 0; i < d.length; i++) {
          row[getCharByIndex(i)] = d[i];
        }
        list.push(row);
      });
      for (let i = 0; i < maxCols; i++) {
        title.push(getCharByIndex(i));
      }
      importTitle.value = title;
      importData.value = list;
      importDataAoa.value = aoa;
    };
    reader.readAsArrayBuffer(file);
    return false;
  };

  /* 导入excel读取合并信息 */
  const importFile3 = (file: File) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const data = new Uint8Array(e.target?.result as any);
      const workbook = XLSX.read(data, { type: 'array' });
      const sheetNames = workbook.SheetNames;
      const worksheet = workbook.Sheets[sheetNames[0]];
      // 解析成二维数组
      const aoa = XLSX.utils.sheet_to_json<string[]>(worksheet, { header: 1 });
      // 生成表格需要的数据
      let list: Record<string, any>[] = [];
      let maxCols = 0;
      let title: string[] = [];
      aoa.forEach((d) => {
        if (d.length > maxCols) {
          maxCols = d.length;
        }
        const row = {};
        for (let i = 0; i < d.length; i++) {
          row[getCharByIndex(i)] = d[i];
        }
        list.push(row);
      });
      for (let i = 0; i < maxCols; i++) {
        title.push(getCharByIndex(i));
      }
      // 记录合并单元格
      if (worksheet['!merges']) {
        worksheet['!merges'].forEach((m) => {
          for (let r = m.s.r; r <= m.e.r; r++) {
            for (let c = m.s.c; c <= m.e.c; c++) {
              const cc = getCharByIndex(c);
              list[r]['__colspan__' + cc] = 0;
              list[r]['__rowspan__' + cc] = 0;
            }
          }
          const char = getCharByIndex(m.s.c);
          list[m.s.r]['__colspan__' + char] = m.e.c - m.s.c + 1;
          list[m.s.r]['__rowspan__' + char] = m.e.r - m.s.r + 1;
        });
      }
      importTitle.value = title;
      importData.value = list;
      importDataAoa.value = aoa;
    };
    reader.readAsArrayBuffer(file);
    return false;
  };

  /* 生成Excel列字母序号 */
  const getCharByIndex = (index: number) => {
    const chars = [
      'A',
      'B',
      'C',
      'D',
      'E',
      'F',
      'G',
      'H',
      'I',
      'J',
      'K',
      'L',
      'M',
      'N',
      'O',
      'P',
      'Q',
      'R',
      'S',
      'T',
      'U',
      'V',
      'W',
      'X',
      'Y',
      'Z'
    ];
    if (index < chars.length) {
      return chars[index];
    }
    const n = parseInt(String(index / chars.length));
    const m = index % chars.length;
    return chars[n] + chars[m];
  };
</script>
