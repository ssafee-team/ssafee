declare interface Shop {
  id: number
  name: string
  phone: string
  address: string
  imageUrl: string
  enabledOrder: boolean
  minimumPrice: number
  closed: boolean
  menuCategories: Array<MenuCategory>
}

declare interface MenuCategory {
  id: number
  name: string
  menus: Array<Menu>
}

declare interface Menu {
  id: number
  name: string
  description: string
  price: number
  imageUrl: string
  soldOut: boolean
  optionCategories: Array<OptionCategory>
}

declare interface OptionCategory {
  id: number
  name: string
  required: boolean
  maxCount: number
  options: Array<Option>
}

declare interface Option {
  id: number
  name: string
  price: number
}

declare interface Room {
  id?: string
  name: string
  generation: number
  classroom: number
  lastOrderTime: string
  createdAt: string
  shopId: number
  userId: number
  creator?: Creator
}

declare interface Creator {
  id: number
  name: string
  bank: string
  account: string
  mattermostWebhookUrl?: string
}

declare interface Order {
  id: number
  confirmedAt: string
  rejectedAt: string
  madeAt: string
  deliveredAt: string
  createdAt: string
  choiceMenus?: Array<ChoiceMenu>
}

declare interface Participant {
  id: number
  name: string
  pickedCarrier: boolean
  paid: boolean
}

declare interface ChoiceMenu {
  id: number
  participantId: number
  participantName: string
  menu: Menu
  optionCategories: Array<OptionCategory>
}

declare interface ChoiceOptionCategory {
  id: number
  optionCategory: OptionCategory
  options: Array<Option>
}

declare interface Chat {
  id: number
  name: string
  content: string
  createdAt: string
}

declare interface CreatorJwt {
  id: number
  authority: string
  iat: number
  exp: number
}

declare interface ManagerJwt {
  id: number
  shopId: number
  authority: string
  iat: number
  exp: number
}
