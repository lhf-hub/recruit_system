<template>
  <div class="app-navbar-actions">
    <VaSwitch v-model="value" color="#5123a1" off-color="#ffd300" style="--va-switch-checker-background-color: #252723">
      <template #innerLabel>
        <div class="va-text-center">
          <VaIcon :name="value ? 'dark_mode' : 'light_mode'" />
        </div>
      </template>
    </VaSwitch>
    <GithubButton v-if="!isMobile" class="app-navbar-actions__item" />
    <ProfileDropdown class="app-navbar-actions__item app-navbar-actions__item--profile mr-1" />
  </div>
</template>

<script lang="ts" setup>
import ProfileDropdown from './dropdowns/ProfileDropdown.vue'
import NotificationDropdown from './dropdowns/NotificationDropdown.vue'
import GithubButton from './GitHubButton.vue'

defineProps({
  isMobile: { type: Boolean, default: false },
})

import { useI18n } from 'vue-i18n'
import { ref, watch } from 'vue'
const value = ref(false)
const { t } = useI18n()
import { computed } from 'vue'

import { useColors } from 'vuestic-ui'

const { applyPreset, currentPresetName } = useColors()

const theme = computed({
  get() {
    return currentPresetName.value
  },
  set(value) {
    applyPreset(value)
  },
})

watch(value, (newValue) => {
  theme.value = newValue ? 'dark' : 'light'
})

const options = [
  { label: t('buttonSelect.dark'), value: 'dark' },
  { label: t('buttonSelect.light'), value: 'light' },
]
</script>

<style lang="scss">
.app-navbar-actions {
  display: flex;
  align-items: center;

  .va-dropdown__anchor {
    color: var(--va-primary);
    fill: var(--va-primary);
  }

  &__item {
    padding: 0;
    margin-left: 0.25rem;
    margin-right: 0.25rem;

    svg {
      height: 20px;
    }

    &--profile {
      display: flex;
      justify-content: center;
    }

    .va-dropdown-content {
      background-color: var(--va-white);
    }

    @media screen and (max-width: 640px) {
      margin-left: 0;
      margin-right: 0;

      &:first-of-type {
        margin-left: 0;
      }
    }
  }

  .fa-github {
    color: var(--va-on-background-primary);
  }
}
</style>
