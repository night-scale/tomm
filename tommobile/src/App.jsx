import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

const Tab = createBottomTabNavigator();

// 四个页面组件
const HomeScreen = () => (
  <View>
    <Text>Home Screen</Text>
  </View>
);

const SearchScreen = () => (
  <View>
    <Text>Search Screen</Text>
  </View>
);

const NotificationsScreen = () => (
  <View>
    <Text>Notifications Screen</Text>
  </View>
);

const ProfileScreen = () => (
  <View>
    <Text>Profile Screen</Text>
  </View>
);

// 中间新增按钮组件
const AddPostButton = ({ navigation }) => (
  <TouchableOpacity
    style={{
      position: 'absolute',
      bottom: 10,
      left: '50%',
      transform: [{ translateX: -30 }],
      backgroundColor: 'blue',
      padding: 10,
      borderRadius: 30,
    }}
    onPress={() => navigation.navigate('AddPost')}>
    <Text style={{ color: 'white' }}>+</Text>
  </TouchableOpacity>
);

const AddPostScreen = () => (
  <View>
    <Text>Create a new Post</Text>
  </View>
);

const App = () => {
  return (
    <NavigationContainer>
      <Tab.Navigator tabBarOptions={{ showLabel: false }}>
        <Tab.Screen name="Home" component={HomeScreen} />
        <Tab.Screen name="Search" component={SearchScreen} />
        <Tab.Screen
          name="AddPost"
          component={AddPostScreen}
          options={{
            tabBarButton: (props) => <AddPostButton {...props} />,
          }}
        />
        <Tab.Screen name="Notifications" component={NotificationsScreen} />
        <Tab.Screen name="Profile" component={ProfileScreen} />
      </Tab.Navigator>
    </NavigationContainer>
  );
};

export default App;
