<template>
  <div class="profile-dropdown-wrapper">
    <VaDropdown v-model="isShown" :offset="[9, 0]" class="profile-dropdown" stick-to-edges>
      <template #anchor>
        <VaButton preset="secondary" color="textPrimary">
          <span class="profile-dropdown__anchor min-w-max">
            <slot />
            <VaAvatar :size="32" color="warning"> 😍 </VaAvatar>
          </span>
        </VaButton>
      </template>
      <VaDropdownContent
        class="profile-dropdown__content md:w-60 px-0 py-4 w-full"
        :style="{ '--hover-color': hoverColor }"
      >
        <VaList v-for="group in options" :key="group.name">
          <header v-if="group.name" class="uppercase text-[var(--va-secondary)] opacity-80 font-bold text-xs px-4">
            {{ group.name }}
          </header>
          <template v-for="item in group.list" :key="item.name">
            <VaListItem
              v-if="item.role.includes(store.role)"
              class="menu-item px-4 text-base cursor-pointer h-8"
              v-bind="resolveLinkAttribute(item)"
            >
              <!--              <VaIcon :name="item.icon" class="pr-1" color="secondary" />-->
              {{ item.name }}
            </VaListItem>
          </template>

          <VaListSeparator v-if="group.separator" class="mx-3 my-2" />
        </VaList>
      </VaDropdownContent>
    </VaDropdown>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { useColors } from 'vuestic-ui'

import { useUserStore } from '../../../../stores/user-store'

const store = useUserStore()

const { colors, setHSLAColor } = useColors()
const hoverColor = computed(() => setHSLAColor(colors.focus, { a: 0.1 }))

const { t } = useI18n()

type ProfileListItem = {
  name: string
  to?: string
  href?: string
  icon: string
  role: string[]
}

type ProfileOptions = {
  name: string
  separator: boolean
  list: ProfileListItem[]
}

withDefaults(
  defineProps<{
    options?: ProfileOptions[]
  }>(),
  {
    options: () => [
      {
        name: '账号',
        separator: true,
        list: [
          {
            name: '应聘统计',
            to: 'admin-statistic',
            icon: 'mso-favorite',
            role: ['admin'],
          },
          {
            name: '应聘统计',
            to: 'super-admin-statistic',
            icon: 'mso-favorite',
            role: ['super-admin'],
          },
          {
            name: '岗位管理',
            to: 'post-manage',
            icon: 'mso-favorite',
            role: ['super-admin'],
          },
          {
            name: '用户管理',
            to: 'user-manage',
            icon: 'mso-favorite',
            role: ['super-admin'],
          },
          {
            name: '简历管理',
            to: 'resume-manage',
            icon: 'mso-favorite',
            role: ['admin'],
          },
          {
            name: '面试管理',
            to: 'interview-manage',
            icon: 'mso-favorite',
            role: ['admin'],
          },
          {
            name: '笔试管理',
            to: 'written-manage',
            icon: 'mso-favorite',
            role: ['admin'],
          },
        ],
      },
      {
        name: '',
        separator: false,
        list: [
          {
            name: '登出',
            to: 'login',
            icon: 'mso-logout',
            role: ['admin', 'super-admin'],
          },
        ],
      },
    ],
  },
)

const isShown = ref(false)

const resolveLinkAttribute = (item: ProfileListItem) => {
  return item.to ? { to: { name: item.to } } : item.href ? { href: item.href, target: '_blank' } : {}
}
</script>

<style lang="scss">
.profile-dropdown {
  cursor: pointer;

  &__content {
    .menu-item:hover {
      background: var(--hover-color);
    }
  }

  &__anchor {
    display: inline-block;
  }
}
</style>
