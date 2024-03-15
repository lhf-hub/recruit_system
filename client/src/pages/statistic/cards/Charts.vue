<template>
  <div class="gap-4" style="display: flex; flex-direction: column">
    <div style="display: flex" class="gap-4">
      <canvas ref="educationChart" style="max-height: 50vh; max-width: 40%; min-height: 50vh; min-width: 40%"></canvas>
      <canvas ref="jobChart" style="max-height: 50vh; max-width: 60%; min-height: 50vh; min-width: 40%"></canvas>
    </div>
    <div style="display: flex" class="gap-4">
      <canvas ref="departmentChart" style="max-height: 50vh; max-width: 40%; min-height: 50vh; min-width: 40%"></canvas>
      <canvas
        ref="transmissionChart"
        style="max-height: 50vh; max-width: 60%; min-height: 50vh; min-width: 40%"
      ></canvas>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watchEffect } from 'vue'
import { Chart, registerables } from 'chart.js'
import { Statistic } from '../types'
import { sleep } from '../../../services/utils'
import { emit } from 'process'
import { Console } from 'console'
import { watch } from 'fs'

Chart.register(...registerables)

const educationChart = ref(null)
const jobChart = ref(null)
const departmentChart = ref(null)
const transmissionChart = ref(null)

const data = ref<Statistic>({
  resumeNum: 0,
  postNum: 0,
  preRecruitsNum: 0,
  trialNum: 0,
  writtenNum: 0,
  interviewNum: 0,
  hiredNum: 0,
  outNum: 0,
  specialistNum: 0,
  undergraduateNum: 0,
  masterNum: 0,
  doctorNum: 0,
  nameNum: [
    {
      postND: '',
      resumeNum: 0,
    },
  ],
  departNum: [
    {
      postND: '',
      resumeNum: 0,
    },
  ],
})
const props = defineProps<{
  statistic: Statistic
}>()
// const emit = defineEmits<{
//   (event: 'getStatistic', callback: () => {}): void
// }>()

onMounted(() => {
  setTimeout(() => {
    if (educationChart.value) {
      new Chart(educationChart.value, {
        type: 'pie',
        data: {
          labels: ['专科', '本科', '硕士', '博士'],
          datasets: [
            {
              data: [data.value.specialistNum, data.value.undergraduateNum, data.value.masterNum, data.value.doctorNum],
              backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)', 'rgb(255, 205, 86)', 'rgb(75, 192, 192)'],
            },
          ],
        },
        options: {
          plugins: {
            title: {
              display: true,
              text: '学历分析',
              font: {
                size: 20,
              },
            },
          },
          responsive: true,
        },
      })
    }
    if (jobChart.value) {
      new Chart(jobChart.value, {
        type: 'bar',
        data: {
          labels: data.value.nameNum.map((item) => item.postND),
          datasets: [
            {
              data: data.value.nameNum.map((item) => item.resumeNum),
              backgroundColor: 'rgb(54, 162, 235)',
              maxBarThickness: 30, // 设置柱子的最大宽度
            },
          ],
        },
        options: {
          plugins: {
            legend: {
              display: false,
            },
            title: {
              display: true,
              text: '岗位招聘情况',
              font: {
                size: 20,
              },
            },
          },

          responsive: true,
          scales: {
            x: {
              grid: {
                display: false, // 取消 x 轴的网格线
              },
            },
            y: {
              grid: {
                display: false, // 取消 y 轴的网格线
              },
            },
          },
        },
      })
    }

    if (departmentChart.value) {
      new Chart(departmentChart.value, {
        type: 'bar',
        data: {
          labels: data.value.departNum.map((item) => item.postND),
          datasets: [
            {
              data: data.value.departNum.map((item) => item.resumeNum),
              backgroundColor: 'rgb(255, 99, 132)',
              maxBarThickness: 30, // 设置柱子的最大宽度
            },
          ],
        },
        options: {
          plugins: {
            legend: {
              display: false,
            },
            title: {
              display: true,
              text: '部门招聘情况',
              font: {
                size: 20,
              },
            },
          },
          responsive: true,
          scales: {
            x: {
              grid: {
                display: false, // 取消 x 轴的网格线
              },
            },
            y: {
              grid: {
                display: false, // 取消 y 轴的网格线
              },
            },
          },
        },
      })
    }

    if (transmissionChart.value) {
      new Chart(transmissionChart.value, {
        type: 'bar',
        data: {
          labels: ['投递人员', '资格筛选通过人员', '笔试面试通过人员', '拟录用人'],
          datasets: [
            {
              data: [data.value.preRecruitsNum, data.value.writtenNum, data.value.interviewNum, data.value.hiredNum],
              backgroundColor: ['rgb(255, 99, 132)', 'rgb(255, 159, 64)', 'rgb(255, 205, 86)', 'rgb(75, 192, 192)'],
              maxBarThickness: 30, // 设置柱子的最大宽度
            },
          ],
        },
        options: {
          indexAxis: 'y',
          plugins: {
            legend: {
              display: false,
            },
            title: {
              display: true,
              text: '招聘转换率',
              font: {
                size: 20,
              },
            },
          },
          responsive: true,
          scales: {
            x: {
              beginAtZero: false,
              max: 100,
              grid: {
                display: false, // 取消 x 轴的网格线
              },
            },
            y: {
              grid: {
                display: false, // 取消 y 轴的网格线
              },
            },
          },
        },
      })
    }
  }, 200)


})

watchEffect(() => {
  data.value = props.statistic
})
</script>

<style scoped>
canvas {
  box-shadow: 0 2px 3px black;
  padding: 5px;
  border-radius: 7px;
  margin: 5px;
}
</style>
