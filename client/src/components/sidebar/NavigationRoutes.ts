export interface INavigationRoute {
  name: string
  displayName: string
  meta: { icon: string }
  role?: string[]
  children?: INavigationRoute[]
}

export default {
  root: {
    name: '/',
    displayName: 'navigationRoutes.home',
  },
  routes: [

    {
      name: 'admin-statistic',
      displayName: '应聘统计',
      meta: {
        icon: 'bar_chart',
      },
      role: ['admin'],
    },
    {
      name: 'super-admin-statistic',
      displayName: '应聘统计',
      meta: {
        icon: 'bar_chart',
      },
      role: ['super-admin'],
    },
    {
      name: 'post-manage',
      displayName: '岗位管理',
      meta: {
        icon: 'group',
      },
      role: ['super-admin'],
    },
    {
      name: 'news-manage',
      displayName: '公告管理',
      meta: {
        icon: 'announcement',
      },
      role: ['super-admin'],
    },
    {
      name: 'resume-manage',
      displayName: '简历管理',
      meta: {
        icon: 'folder_shared',
      },
      role: ['admin'],
    },
    {
      name: 'written-manage',
      displayName: '笔试管理',
      meta: {
        icon: 'edit_document',
      },
      role: ['admin'],
    },
    {
      name: 'interview-manage',
      displayName: '面试管理',
      meta: {
        icon: 'credit_card',
      },
      role: ['admin'],
    },
    {
      name: 'user-manage',
      displayName: '用户管理',
      meta: {
        icon: 'account_circle',
      },
      role: ['super-admin'],
    },
  ] as INavigationRoute[],
}
